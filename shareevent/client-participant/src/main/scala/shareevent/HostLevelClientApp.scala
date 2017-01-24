package shareevent

import akka.actor.ActorSystem
import akka.dispatch.ExecutionContexts
import akka.stream.ActorMaterializer
import shareevent.ClientApp.register

import scala.concurrent.duration._
import scala.concurrent.Await

object HostLevelClientApp extends App with HostLevelClientService {
  implicit val execCtx = ExecutionContexts.global
  implicit val actorSystem: ActorSystem = ActorSystem("client")
  implicit val materializer = ActorMaterializer()

  val john = ParticipantData("John", "SuperSecure")
  val res1 = Await.result(register(john), 3.seconds)
  val res2 = Await.result(register(john), 3.seconds)
  println(s"Result1: $res1 \nResult2: $res2")
}
