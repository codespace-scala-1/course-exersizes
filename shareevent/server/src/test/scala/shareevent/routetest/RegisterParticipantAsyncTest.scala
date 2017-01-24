package shareevent.routetest

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._

import org.scalatest._
import scala.concurrent.duration._
import scala.concurrent._
import scala.language.postfixOps._


import scala.concurrent.Await

class RegisterParticipantAsyncTest  extends AsyncWordSpec with Matchers with ServerRouteTestCommons  {

  "The service" should {
    "second registration attempt should fail" in {
      pending

      val personJs: JValue = ("login" -> "yar") ~ ("password" -> "test")

      Post("/participant", personJs) ~> route ~> check {

        response.status shouldEqual StatusCodes.Conflict


        val f: Future[String] = response.entity.toStrict(1 second).map(_.data.decodeString("UTF-8"))


        f map { s =>
          s should include("participant already exists")
        }


      }
    }

  }


}
