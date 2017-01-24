package shareevent

import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.stream.scaladsl.{Sink, Source}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport

import scala.concurrent.Future
import java.util.UUID

import scala.util.{Failure, Success, Try}

trait HostLevelClientService extends GenericClientService with Json4sSupport {
  lazy val hostFlow = Http().cachedHostConnectionPool[UUID]("localhost", 8080)

  def register(userData: ParticipantData): Future[StatusCode] = {
    val futureParticipantData = Marshal(userData).to[RequestEntity]
    val registrationRequest = futureParticipantData map { pjs =>
      val req = HttpRequest(
        HttpMethods.POST,
        "/participant",
        entity = pjs
      )
      val uuid = UUID.randomUUID()
      req -> uuid
    }
    Source.fromFuture(registrationRequest)
      .via(hostFlow)
      .map {
        case (Success(response), _) =>
          response.discardEntityBytes()
          response.status
        case (_,_) => StatusCodes.InternalServerError
      }.runWith(Sink.head)
  }
}
