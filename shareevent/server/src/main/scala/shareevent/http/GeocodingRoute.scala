package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import org.json4s.NoTypeHints
import shareevent.InMemoryParticipantRepository
import shareevent.simplemodel.SParticipant
//import grizzled.slf4j.Logging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


class ServerRoute(implicit actorSystem: ActorSystem,
                           materializer: Materializer,
                           execCtx: ExecutionContext) extends Json4sSupport {
  implicit val formats = Serialization.formats(NoTypeHints)
  implicit val serialization = Serialization
  private val challenge = HttpChallenge("Basic", "realm")

  val participantRepo = new InMemoryParticipantRepository

  val myUserPassAuthenticator: Option[BasicHttpCredentials] => Future[AuthenticationResult[Int]] =
    userPass => {
      val maybeUser = userPass flatMap { case BasicHttpCredentials(user, password) =>
        if (user == "me" && password == "me") Some(1) else None
      }
      val authResult = maybeUser match {
        case Some(user) => Right(user)
        case None       => Left(challenge)
      }
      Future.successful(authResult)
    }

  val route =
    path("participant") {
      (post & entity(as[SParticipant])) { participant =>
        val storeResult = participantRepo.retrieve(participant.login) flatMap {maybeExisting =>
          if (maybeExisting.isDefined) {
            Future.failed(new Exception(s"participant already exists"))
          } else {
            participantRepo.store(participant) map (_ => participant)
          }
        }
        onComplete(storeResult) {
          case Success(entity) => complete(OK -> write(entity))
          case Failure(ex)    => complete(Conflict -> ex.getMessage)
        }
      } ~ authenticateOrRejectWithChallenge(myUserPassAuthenticator).apply { userId =>
        (delete & parameter('login.as[String])){ login =>
          complete(InternalServerError -> "Not implemented") // TODO: implement this and authenticator above
        }
      }
    }
}
