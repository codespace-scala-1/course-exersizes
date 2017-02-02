package shareevent.actorbased

import akka.persistence.{PersistentActor, SnapshotOffer}
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}

import scala.concurrent.Future

sealed trait IdSetMessage
sealed trait IdSetStateChangeMessage extends IdSetMessage
case class NewId[K](x:K) extends IdSetStateChangeMessage
case class RemoveId[K](x:K) extends IdSetStateChangeMessage
case class Query(expr:QueryExpression[_]) extends IdSetMessage


class IdSetHolder[K,T](name:String, meta: ObjectMeta[T,_]) extends PersistentActor
{
  import shareevent.persistence.QueryDSL._

  var state: Set[K] = Set()


  override def receiveRecover: Receive = {
    case x: IdSetStateChangeMessage => updateState(x)
    case SnapshotOffer(_,snapshot) => //saveSnapshot(s)
  }

  def updateState: Receive =
  {
    case NewId(x) => state += (x.asInstanceOf[K])
    case RemoveId(x) => state -= x.asInstanceOf[K]
  }

  override def receiveCommand: Receive =
  {
    case x: IdSetStateChangeMessage => persistAll(scala.collection.immutable.Seq[IdSetStateChangeMessage](x))(updateState)
    case Query(expr) =>
      sender ! performQuery(expr)
  }

  def performQuery[T](expr:QueryExpression[T]):Future[Seq[T]] = ???

  override def persistenceId: String = name
}
