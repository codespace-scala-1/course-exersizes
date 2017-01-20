package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.marshalling.Marshaller._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.{BasicHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import org.json4s.JsonAST.JInt
import shareevent.model.{Person, Role}
import shareevent.model.Role.Role
import shareevent.{DomainContext, DomainService}

import scala.util.Try
//import grizzled.slf4j.Logging
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.json4s.native.JsonMethods._
import org.json4s.ext.EnumSerializer
import org.json4s.JsonAST.{JField, JObject, JString, JValue}
import org.json4s.{CustomSerializer, Extraction, NoTypeHints}


import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


class ServerRoute(implicit actorSystem: ActorSystem,
                  materializer: Materializer,
                  execCtx: ExecutionContext,
                  context: DomainContext,
                  service: DomainService) extends Json4sSupport {
  //important: both formats and serialization implicits need to be in scope
  val defaultFormats = Serialization.formats(NoTypeHints) + new EnumSerializer(Role)
  val personFormats = new CustomSerializer[Person](formats => (
    { case p: JObject =>
      implicit val formats = defaultFormats
      val pWithRole = p merge JObject(JField("role", JInt(Role.Participant.id)) :: Nil) //TODO: what happens if JObject already contains role
      pWithRole.extract[Person]
    },
    PartialFunction.empty
    ))

  implicit val formats = defaultFormats + personFormats
  implicit val serialization = Serialization
  private val challenge = HttpChallenge("Basic", "realm")

  def authenticated(login: String, password: String): Boolean = {

    context.repository.personDAO.retrieve(login) match {
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
          for {op <- context.repository.personDAO.retrieve(participant.login)
               _ <- op.map(p => new IllegalArgumentException("participant already exists")).toLeft[Unit](()).toTry
               stored <- context.repository.personDAO.store(participant)
          } yield stored

        onComplete(Future.fromTry(storeResult)) {
          case Success(entity) => complete(OK -> entity)
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
              context.repository.personDAO.retrieve(login) match {
                case Success(_) => complete(OK -> "DONE")
                case Failure(ex) => complete(Conflict -> ex.getMessage)
              }
            }
          }
        }
      }
    }

}
