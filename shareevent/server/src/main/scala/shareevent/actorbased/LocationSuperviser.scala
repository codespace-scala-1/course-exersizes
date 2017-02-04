package shareevent.actorbased

import scala.language.postfixOps
import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.{Backoff, BackoffSupervisor}

import scala.concurrent.duration._

case class CreateActor(props:Props,name:String)

class LocationSuperviser extends Actor
{

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _ => SupervisorStrategy.Escalate
  }

  override def receive: Receive =
  {
    case CreateActor(props,name) =>

      val ref = context.actorOf(props,name)  // actorPath/name
                //actorSystem.actorOf(props,name) => /user/name
      sender() ! ref
  }

}

object LocationSupervisor {

  def createOrFind()(implicit as:ActorSystem):ActorRef =
    as.actorOf(props,name)

  def props:Props =
    Props(classOf[LocationSuperviser])

  def name="location"

}
