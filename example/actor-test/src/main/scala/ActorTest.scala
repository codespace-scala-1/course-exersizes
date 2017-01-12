import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._


object ActorTest {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem()

    implicit val timeout = Timeout(5 seconds)

    val newActor = system.actorOf(Props[TestActor])
    val responseFuture = newActor ? "Hi"
    println(s"received response ${Await.result(responseFuture, 1 second)}")
    system.terminate()
  }
}
