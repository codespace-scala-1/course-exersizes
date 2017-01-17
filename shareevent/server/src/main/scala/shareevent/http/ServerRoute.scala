package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import org.json4s.NoTypeHints
import shareevent.model.Person
import shareevent.{DomainContext, DomainService}

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
                  context: DomainContext,
                  service: DomainService) extends Json4sSupport {
  implicit val formats = Serialization.formats(NoTypeHints)
  implicit val serialization = Serialization
  private val challenge = HttpChallenge("Basic", "realm")

  def authenticated(login: String, password: String): Boolean = {

    context.repository.retrieveParticipant(login) match {
      case Success(Some(participant)) => participant.password == password
      case _ => false
    }
  }

  val myUserPassAuthenticator: Option[BasicHttpCredentials] => Future[AuthenticationResult[String]] =
    userPass => {

      val maybeUser = for{ BasicHttpCredentials(login, password) <- userPass if authenticated(login, password)} yield login

      val authResult = maybeUser.toRight(challenge)

      Future.successful(authResult)
    }

  val route =
    path("participant") {
      //TODO:  serialize person without role
      (post & entity(as[Person])) { participant =>

        val storeResult:Try[Person] =
          for { op <- context.repository.retrieveParticipant(participant.login)
                t <- op.toLeft({context.repository.storeParticipant(participant: Person) map (_ => participant)})
          } yield t

        // TODO:  rewrite use idiomatic loops.
        /*
        val storeResult:Try[Person] = context.repository.retrieveParticipant(participant.login) flatMap { maybeExisting =>
          if (maybeExisting.isDefined) {
              Failure(new Exception(s"participant already exists"))
          } else {
              context.repository.storeParticipant(participant) map (_ => participant)
          }
        }
        */

        onComplete(Future.fromTry(storeResult)) {
          case Success(entity) => complete(OK -> write(entity))
          case Failure(ex)    => complete(Conflict -> ex.getMessage)
        }
      } ~
      delete {
        authenticateOrRejectWithChallenge(myUserPassAuthenticator).apply { userId =>
          parameter('login.as[String]) { login =>

            if(userId != login) {
              complete(Conflict -> "Not authorized to delete other participant")
            }
            else {
              context.repository.deleteParticipant(login) match {
                case Success(_) => complete(OK -> "DONE")
                case Failure(ex) => complete(Conflict -> ex.getMessage)
              }
            }
          }
        }
      }
    }

}
