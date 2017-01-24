package shareevent

import akka.actor.ActorSystem
import akka.dispatch.ExecutionContexts
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.concurrent.duration._


override object ClientApp extends App with ClientService {
  implicit val execCtx = ExecutionContexts.global
  implicit val actorSystem: ActorSystem = ActorSystem("client")
  implicit val materializer = ActorMaterializer()

  val john = ParticipantData("John", "SuperSecure")
  Await.result(register(john), 3.seconds)
  Await.result(register(john), 3.seconds)
}
