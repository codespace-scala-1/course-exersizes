package shareevent.http

import shareevent._

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import shareevent.{DomainInterpeter, InMemoryContext}

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends App {
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val repository = new InMemoryContext()
  implicit val service: DomainInterpeter = new simplemodel.Interpreter

  val route = new ServerRoute().route

  Http().bindAndHandle(route, "0.0.0.0", 8080)
}
