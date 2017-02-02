package shareevent.actorbased

import akka.actor.Actor
import akka.actor.Actor.Receive

class IdSetWriter extends Actor {

  override def receive: Receive =
  {
    case x => System.out.println(x)
  }

}
