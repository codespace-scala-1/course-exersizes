package shareevent

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, RequestEntity}
import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.NoTypeHints
import org.json4s.native.Serialization

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

trait ClientService extends GenericClientService with Json4sSupport {

  def register(userData: ParticipantData): Future[Unit] = {
    val futureParticipantData = Marshal(userData).to[RequestEntity]
    futureParticipantData.flatMap { pjs =>
      Http().singleRequest(
        HttpRequest(
          HttpMethods.POST,
          uri = "http://localhost:8080/participant",
          entity = pjs
        )
      )
    } flatMap { resp =>
      if (resp.status == StatusCodes.OK) {
        Future.successful(())
      } else {
        val futureMessage = resp.entity.toStrict(3.seconds).map(resp.status + ": " + _.data.decodeString("UTF-8"))
        futureMessage.flatMap (msg => Future.failed(new Exception(msg)))
      }
    }
  }

}
