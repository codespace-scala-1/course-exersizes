package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.settings.ConnectionPoolSettings
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.marshalling.{Marshaller, ToResponseMarshallable, ToResponseMarshaller}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, Materializer}
import akka.util.ByteString
//import grizzled.slf4j.Logging
import org.json4s.JsonAST._
import org.json4s.jackson.JsonMethods._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.io.StdIn
import scala.util.{Failure, Success}


class ServerRoute(implicit actorSystem: ActorSystem,
                     materializer: Materializer,
                     execCtx: ExecutionContext,
                     val validationContext: ValidationContext) extends JsonSupport {

  lazy val authCode = ConfigFactory.load().getString("geocoder.ca.authenticationCode")
  val geocoderCaUri1: Uri = "http://geocoder.ca" //TODO: switch to backup server (backup-geocoder.ca) if this fails
  implicit val optStringParser: JsonParser[Option[String]] = GCAOptionalStringParser()
  val stdAddressParser = JsonParser[StandardAddress]
  val coordsParser = JsonParser[Coordinates]
  val errorParser = JsonParser[Error]
  /*implicit val succWriter = JsonWriter[SuccessResponse]
  implicit val errWriter = JsonWriter[ErrorResponse]
  implicit val stdResponseWriter = JsonWriter[StandardResponse]*/

  implicit def stringStreamMarshaller(implicit ec: ExecutionContext): ToResponseMarshaller[Source[String, Unit]] =
    Marshaller.withFixedContentType(ContentTypes.`text/plain(UTF-8)`) { s =>
      HttpResponse(entity = HttpEntity.CloseDelimited(ContentTypes.`text/plain(UTF-8)`, s.map(ByteString(_))))
    }

  val testAuthenticator: Option[BasicHttpCredentials] => Future[AuthenticationResult[Unit]] =
    userPass => {
      val maybeSuccess = userPass map { case BasicHttpCredentials(user, password) => user == "me" && password == "me" }
      val authResult = maybeSuccess match {
        case Some(true) => Right(())
        case _ => Left(HttpChallenge("Basic", "Optrak"))
      }
      Future.successful(authResult)
    }

  def transformResponse(r: HttpResponse): Future[StandardResponse] =
    r.entity.toStrict(1.second) map { entity =>
      val sJson = entity.data.decodeString("UTF-8")
      val json = parse(sJson)
      implicit val vCtx = HeadContext("parsing geocoder.ca response")

      val dStdResponse = for {
        stdAddr <- stdAddressParser.parse(json \ "standard")("standard address").toXor
        coords <- coordsParser.parse(json)("coordinates").toXor
      } yield {
        val maybeCountry = json.findField{
          case JField("country", _) => true
          case _ => false
        }.map{case (_, countryJson) => countryJson.values.toString}
        SuccessResponse(Some(stdAddr.toAddress.copy(country = maybeCountry)), coords.latt, coords.longt, None)
      }
      // todo yar - sort out
      /*
      dStdResponse.toValidated
        .findSuccess(errorParser.parse(json \ "error")("error").map(_.toErrorResponse))
        .valueOr(nel => ErrorResponse(None, nel.toString.replace("\n", "\n ")))
        //\n is not supported in JSON, so just making the string a little more readable until it is treated by frontend
        */
      // takes the error from the input and creates an error response
      def validErrorResponse: ValidatedER[StandardResponse] =
        errorParser.parse(json \ "error").map(_.toErrorResponse)

      val response: StandardResponse = findValid(dStdResponse.toValidated, validErrorResponse) match {
        case Valid(ok) => ok
        case Invalid(nel) => ErrorResponse(None, nel.toString.replace("\n", "\n "))
      }
      response
    }

  
  def GeocodeRequest(address: String): HttpRequest =
    HttpRequest(uri = geocoderCaUri1.withQuery(Query(
      "auth" -> authCode,
      "locate" -> address,
      "standard" -> "1", //whether to return the properly formatted "standardized" address in the response.
      "json" -> "1",
      "showcountry" -> "1", //let us have country in response
      "geoit" -> "xml" //output format. Said to be mandatory even if using json
    )))

  val route =
    (get & parameter('address.as[String])) { address =>
      pathPrefix("geocode" / ("us" | "ca")) {
        pathEnd {
          val eventuallyResponse = Http().singleRequest(GeocodeRequest(address))
          onComplete(eventuallyResponse flatMap transformResponse) {
            case Success(s: SuccessResponse) => complete(OK -> s)
            case Success(e@ErrorResponse(code, _)) => code match {
              case Some("008" | "005") => complete(BadRequest -> e)
              case Some("004" | "007") => //was never able to elicit these from the server
                logger.info(s"$address produces $code!")
                complete(BadRequest -> e)
              case _ => complete(InternalServerError -> e)
            }
            case Failure(e) => complete(InternalServerError -> e.getMessage)
          }
        } ~
          path("debug") { // returns raw response from geocoder.ca
            val eventuallyResponse = Http().singleRequest(GeocodeRequest(address))
            onComplete(eventuallyResponse) {
              case Success(r) => complete(r)
              case Failure(e) => complete(InternalServerError -> e.getMessage)
            }
          }
      }
    } ~
    (path("batch-geocode" / ("us" | "ca")) & put & entity(as[Map[String, String]])) { br =>
      val connectionPool = Http().cachedHostConnectionPool[String](
        host = geocoderCaUri1.authority.host.toString,
        settings = ConnectionPoolSettings(actorSystem)//.copy(pipeliningLimit = 1)
      )
      val requests = br.mapValues(GeocodeRequest).toList.map(_.swap)
      // we are lucky connection pool expects (HttpRequest, T), because we can use our string IDs as T
      val ftrRs: Future[List[(String, StandardResponse)]] = Source(requests)
        .via(connectionPool)
        .mapAsync(4){ case (tryResponse, id) =>
          Future.fromTry(tryResponse)
            .flatMap(transformResponse).recover{
              case t: Throwable => ErrorResponse(None, t.getMessage)
            }
            .map(id -> _)
        }.runFold(List[(String, StandardResponse)]())((a,b) => b :: a)

      onSuccess(ftrRs){ case rs =>
          //TODO: if all requests fail, return not OK
          complete(OK -> rs.toMap)
          // be aware that actual json contains extra field names indicating whether it is a Success or an Error, see
          // the "batch geocode request should succeed" test for details
      }
    }
}
