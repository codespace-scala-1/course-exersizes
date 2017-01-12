package shareevent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext.Implicits.global

object Server extends App {
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val route = new ServerRoute().route
  Http().bindAndHandle(route, "0.0.0.0", 8080)
}
