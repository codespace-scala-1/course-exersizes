package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import shareevent.InMemoryParticipantRepository
import shareevent.simplemodel.SParticipant
//import grizzled.slf4j.Logging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


class ServerRoute(implicit actorSystem: ActorSystem,
                  materializer: Materializer,
                  execCtx: ExecutionContext) extends Json4sSupport {

  val participantRepo = new InMemoryParticipantRepository
  val testAuthenticator: Option[BasicHttpCredentials] => Future[AuthenticationResult[Unit]] =
    userPass => {
      val maybeSuccess = userPass map { case BasicHttpCredentials(user, password) => user == "me" && password == "me" }
      val authResult = maybeSuccess match {
        case Some(true) => Right(())
        case _ => Left(HttpChallenge("Basic", "Optrak"))
      }
      Future.successful(authResult)
    }

  val route =
    path("participant") {
      (post & entity(as[SParticipant])) { participant =>
        val stotrResult = participantRepo.retrieve(participant.login) flatMap { maybeExisting =>
          if (maybeExisting.isDefined) {
            Future.failed(new Exception(s"participant already exists"))
          } else {
            participantRepo.store(participant) map (_ => participant)
          }
        }
        onComplete(stotrResult) {
          case Success(entity) => complete(OK -> write(entity))
          case Failure(ex) => complete(Conflict -> ex.getMessage)
        }
      }
      }

      /*(get & parameter('address.as[String])) { address =>
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
      }*/
    }
