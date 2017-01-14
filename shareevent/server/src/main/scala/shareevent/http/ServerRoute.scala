package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import org.json4s.NoTypeHints
import shareevent.{DomainInterpeter, DomainRepository}
import shareevent.simplemodel.SParticipant

import scala.util.Try
//import grizzled.slf4j.Logging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


class ServerRoute(implicit actorSystem: ActorSystem,
                           materializer: Materializer,
                           execCtx: ExecutionContext,
                  repository: DomainRepository[SParticipant],
                  service: DomainInterpeter) extends Json4sSupport {
  implicit val formats = Serialization.formats(NoTypeHints)
  implicit val serialization = Serialization
  private val challenge = HttpChallenge("Basic", "realm")


  val myUserPassAuthenticator: Option[BasicHttpCredentials] => Future[AuthenticationResult[String]] =
    userPass => {

      val maybeUser = for{ BasicHttpCredentials(user,password) <- userPass if user=="me" && password == "me" } yield user

      val authResult = maybeUser.toRight(challenge)

      Future.successful(authResult)

    }

  val route =
    path("participant") {
      (post & entity(as[SParticipant])) { participant =>
        val storeResult:Try[SParticipant] = repository.retrieveParticipant(participant.login) flatMap {maybeExisting =>
          if (maybeExisting.isDefined) {
              Failure(new Exception(s"participant already exists"))
          } else {
              repository.storeParticipant(participant) map (_ => participant)
          }
        }

        onComplete(Future.fromTry(storeResult)) {
          case Success(entity) => complete(OK -> write(entity))
          case Failure(ex)    => complete(Conflict -> ex.getMessage)
        }
      } ~
      delete {
        authenticateOrRejectWithChallenge(myUserPassAuthenticator).apply { userId =>
          parameter('login.as[String]) { login =>
            complete(InternalServerError -> "Not implemented") // TODO: implement this and authenticator above
          }
        }
      }
    }

}
