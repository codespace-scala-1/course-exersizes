package shareevent.actorbased

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import akka.util.Timeout
import shareevent.model.{Booking, BookingStatus, Coordinate, Event, Location, ScheduleItem}

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

sealed trait LocationMessage



case object HiLocationMessage extends LocationMessage

case class PreBook(scheduleItem: ScheduleItem) extends LocationMessage
case class Confirm(scheduleItem: ScheduleItem) extends LocationMessage
case class Cancel(scheduleItem: ScheduleItem) extends LocationMessage
case class CancelEvent(id: Event.Id) extends LocationMessage

case class PreBookReply(boolean: Boolean) extends LocationMessage



class LocationActor(id:Location.Id, name:String, capacity:java.lang.Integer,coordinate:Coordinate) extends PersitentActor {

  //val (id)

  var state: Location = Location(Some(id),name,capacity,coordinate)

  override def receive: Receive = {

    case HiLocationMessage =>
      System.err.println("Hi received!")
    case PreBook(item) => {
      System.err.println("PreBook received!")
      val oldBookings = state.bookings
      val r = addBooking(oldBookings) match {
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
      val index = oldBookings.indexWhere(booking => item.eventId == booking.eventId)
      if (index != -1) {
        val newBooking = oldBookings(index).copy(status=BookingStatus.Final)

        val newBookings = oldBookings.patch(index,Seq(newBooking),1)
        state = state.copy(bookings = newBookings)
      } else {
        //TODO: better
        logicSupervisor ! "impossible"
      }

    case Cancel(item) =>
      val newBookings = state.bookings.filter(b => b.eventId == item.eventId)
      state = state.copy(bookings = newBookings)

    case CancelEvent(event) =>
      val newBookings = state.bookings.filter(b => b.eventId == event.id)
      state = state.copy(bookings = newBookings)
    //case _ => ???
  }

  def logicSupervisor: ActorRef = ???

  def addBooking(bookingg: Seq[Booking]):Try[Seq[Booking]] = ???



}

object LocationActor {

  def props(id: Location.Id, name: String, capacity: Int, coordinate: Coordinate): Props =
    Props(classOf[LocationActor], id.id, name, capacity, coordinate)

  def createOrFind(id: Location.Id,
                   name: String
                  )(implicit actorSystem:ActorSystem): ActorRef = {
    val properties = props(id, name, 5, Coordinate(0, 0))
    actorSystem.actorOf(properties,actorName(id))
  }

  def find(id:Location.Id)(implicit actorSystem:ActorSystem): Future[ActorRef] =
  {
    actorSystem.actorSelection("/user/"+actorName(id)).resolveOne(1 seconds)
  }

  def actorName(id:Location.Id) = s"location-${id.id}"

}