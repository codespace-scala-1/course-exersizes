package shareevent.http

import shareevent._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import shareevent.DomainService
import shareevent.persistence.inmem.InMemoryContext

import scala.concurrent.ExecutionContext.Implicits.global

object Server extends App {
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val repository = new InMemoryContext()
  implicit val service: DomainService = new simplemodel.SimpleService

  val route = new ServerRoute().route

  val binding = Http().bindAndHandle(route, "0.0.0.0", 8080)

  System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
  System.in.read(); // let it run until user presses return

  binding
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => actorSystem.terminate()) // and shutdown when done
}
