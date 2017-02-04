package shareevent.actorbased

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.persistence.{PersistentActor, SnapshotOffer}
import akka.pattern.ask
import akka.util.Timeout
import org.joda.time.Interval

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}
import shareevent.model._

sealed trait LocationCommand
sealed trait LocationQuery
sealed trait LocationReplyMessage


case object HiLocationMessage extends LocationCommand

case class PreBook(scheduleItem: ScheduleItem) extends LocationCommand
case class Confirm(scheduleItem: ScheduleItem) extends LocationCommand
case class Cancel(scheduleItem: ScheduleItem) extends LocationCommand
case class CancelEvent(id: Event.Id) extends LocationCommand
case object GetState extends LocationQuery

case class PreBookReply(boolean: Boolean) extends LocationReplyMessage




class LocationActor(id:Location.Id, name:String, capacity:java.lang.Integer,coordinate:Coordinate) extends PersistentActor {

  //val (id)

  var state: Location = Location(Some(id),name,capacity,coordinate)

  def updateState: LocationCommand => Unit = {
    case HiLocationMessage =>
      System.err.println("Hi received!")
    case PreBook(item) => {
      System.err.println("PreBook received!")
      val oldBookings = state.bookings
      val r = addBooking(oldBookings, item) match {
        case Failure(ex) => false
        case Success(newBookings) =>
              state = state.copy(bookings = newBookings)
              true
      }
      //val t = Timeout(10 seconds)
      sender ! PreBookReply(r)
    }
    case Confirm(item) =>
      val oldBookings = state.bookings
      val index = oldBookings.indexWhere(booking =>
                                item.eventId == booking.eventId)
      if (index != -1) {
        val newBooking = oldBookings(index).copy(status=BookingStatus.Final)

        val newBookings = oldBookings.patch(index,Seq(newBooking),1)
        state = state.copy(bookings = newBookings)
      } else {
        //TODO: better
        logicSupervisor ! "impossible"
      }

    case Cancel(item) =>
      //TODO: add another condition for filterNot to match on event time
      val newBookings = state.bookings.filterNot(_.eventId == item.eventId)
      state = state.copy(bookings = newBookings)

    case CancelEvent(event) =>
      val newBookings = state.bookings.filterNot(_.eventId == event.id)
      state = state.copy(bookings = newBookings)
    //case _ => ???
  }

  def logicSupervisor: ActorRef = ???

  def addBooking(bookings: Seq[Booking], item: ScheduleItem):Try[Seq[Booking]] =
  {
    @tailrec
    def add(prev:Seq[Booking],current:Seq[Booking]):Seq[Booking]=
    {

      //TODO: retrieve event ?
      val itemInterval = new Interval(item.time,item.time.plusHours(2))

      def createBooking(): Booking =
        new Booking( itemInterval ,item.eventId)

      current.headOption match {
        case None => (createBooking() +: prev).reverse
        case Some(h) => if (h.time.isBefore(item.time)) {
          add(h +: prev, current.tail)
        }  else if (h.time.isAfter(itemInterval.getEnd)) {
          (createBooking() +: prev).reverse ++ current.tail
        }  else {
          throw new IllegalArgumentException("time is overlapped")
        }
      }

    }
    Try(add(Nil,bookings))
  }

  override def receiveRecover: Receive = {
    case msg: LocationCommand => updateState(msg)
      //context.system.eventStream.
    case SnapshotOffer(metadata,snapshot:Location) =>
              state = snapshot
  }

  override def receiveCommand: Receive = {
    case message: LocationCommand =>
           persist(message)(updateState)
    case SnaphotEvent => saveSnapshot(state)
    case GetState => sender ! state
  }

  override def persistenceId: String = s"location-${state.id.get}"

}

object LocationActor {

  def props(id: Location.Id, name: String, capacity: Int, coordinate: Coordinate): Props =
    Props(classOf[LocationActor], id.id, name, capacity, coordinate)

  def propsFromLocation(location: Location): Props =
    props(location.id.get, location.name, location.capacity, location.coordinate)

  def createOrFind(id: Location.Id,
                   name: String
                  )(implicit actorSystem:ActorSystem): Future[ActorRef] = {
    val properties = props(id, name, 5, Coordinate(0, 0))
    val supervisor = LocationSupervisor.createOrFind()
    implicit val timeout = Timeout(1 minute) // TODO: timeout in settings
    (supervisor ? CreateActor(properties,name)).mapTo[ActorRef]
  }

  def find(id:Location.Id)(implicit actorSystem:ActorSystem): Future[ActorRef] =
  {
    val supervisorName = LocationSupervisor.name
    actorSystem.actorSelection(s"/user/${supervisorName}/${actorName(id)}").resolveOne(1 seconds)
  }


  def actorName(id:Location.Id) = id.toString


}