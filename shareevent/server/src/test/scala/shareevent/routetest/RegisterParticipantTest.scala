package shareevent.routetest

import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{CustomSerializer, DefaultFormats, Extraction, NoTypeHints}
import org.json4s.JsonAST._
import shareevent.http.ServerRoute
import shareevent.model.{Person, Role}
import shareevent.{DomainService, simplemodel}
import shareevent.persistence.inmem.InMemoryContext
import org.json4s.JsonDSL._
import org.json4s.ext.EnumSerializer
import org.json4s.native.Serialization
import org.json4s.native.JsonMethods._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Success
import scala.language.postfixOps

class RegisterParticipantTest extends WordSpec with Matchers with ServerRouteTestCommons {
  //implicit val actorSystem = ActorSystem()
  //implicit val materializer = ActorMaterializer()

  "The service" should {
    "register a participant and return it back" in {
      // tests:
      val personJs: JValue = ("login" -> "yar") ~ ("password" -> "AnyPassword")
      val person = Person("yar", "AnyPassword", Role.Participant)
      Post("/participant", personJs) ~> route ~> check {
        response.status shouldEqual StatusCodes.OK
        val jsResponse = responseAs[JValue]
        jsResponse \ "role" shouldEqual JInt(Role.Participant.id)
      }
    }

    "second registration attempt should fail" in {

      val personJs: JValue = ("login" -> "yar") ~ ("password" -> "test")

      Post("/participant", personJs) ~> route ~> check {
        response.status shouldEqual StatusCodes.Conflict


        val f = response.entity.toStrict(1 second).map(_.data.decodeString("UTF-8"))


        val s = Await.result(f, 1 second)

        s should include("participant already exists")


      }
    }

    "deletion of participant should succeed" in {
       pending
      //addCredentials(BasicHttpCredentials("yar", "test"))
      Delete("/participant?login=yar") ~> route ~> check {
        response.status shouldEqual StatusCodes.OK
      }
    }
  }

}