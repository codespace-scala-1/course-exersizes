package shareevent.actorbased

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.persistence.{PersistentActor, SnapshotOffer}
import shareevent.model.{Coordinate, Location}

case object NextId
case class NextIdReply(id:Long)

class IdGenerator(inName: String) extends PersistentActor {

  var name = inName
  var currentId: Long = 0

  override def receiveRecover: Receive =
  {
    case NextId =>
      currentId = currentId +1
    case SnapshotOffer(_,snapshot: Long) => currentId = snapshot
  }

  override def receiveCommand: Receive =
  {
    case NextId =>
      sender ! NextIdReply(currentId)
      currentId = currentId +1
    case SnaphotEvent =>
       saveSnapshot(currentId)
  }

  override def persistenceId: String = s"idgen-$name"

}

object IdGenerator
{

  def props(name: String): Props =
    Props(classOf[IdGenerator], name)


  def createOrFind(name: String)(implicit actorSystem:ActorSystem): ActorRef = {
    val properties = props(name)
    actorSystem.actorOf(properties,s"idgen-$name")
  }


}