package shareevent.routetest

import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.DefaultFormats
import org.json4s.native.Serialization
import org.scalatest.Suite
import shareevent.http.ServerRoute
import shareevent.persistence.inmem.InMemoryContext
import shareevent.{DomainService, simplemodel}

import scala.util.Try

trait ServerRouteTestCommons extends  ScalatestRouteTest with Json4sSupport {

  this: Suite ⇒

  implicit val repository = new InMemoryContext()
  implicit val service: DomainService[Try] = new simplemodel.SimpleService
  val serverRoute = new ServerRoute()

  implicit val formats = DefaultFormats
  implicit val serialization = Serialization


  val route = serverRoute.route


  }
