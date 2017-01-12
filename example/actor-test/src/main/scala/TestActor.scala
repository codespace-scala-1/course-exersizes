import akka.actor.Actor

class TestActor extends Actor {
  def receive = {
    case "Hi" =>
      println("received Hi")
      sender ! "Hello"
  }
}
