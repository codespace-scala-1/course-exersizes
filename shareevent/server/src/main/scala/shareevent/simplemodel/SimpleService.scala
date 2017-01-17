package shareevent.simplemodel

import org.joda.time.{DateTime, Duration => JodaDuration}
import shareevent.{DomainContext, DomainService}
import shareevent.model._

import scala.util.{Failure, Success, Try}
import org.joda.time.Interval

class SimpleService extends DomainService {


  def createEvent(organizer: Person, title: String, theme: String, organizerCost: Money, duration: JodaDuration, scheduleWindow: JodaDuration, quantityOfParticipants: Int): DomainContext => Try[Event] = {
    require(organizer.role == Role.Organizer && quantityOfParticipants >= Event("","",organizer,organizerCost,Initial,DateTime.now(),duration,scheduleWindow).minParticipantsQuantity)
    _ => Try {
      Event(title, theme, organizer, organizerCost, Initial, DateTime.now(), duration, scheduleWindow)
    }
  }

  override def createLocation(name: String, capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordinate: Coordinate,
                              costs: Money): DomainContext => Try[Location] = {_ => Try(Location(name, capacity, coordinate, Seq.empty))}

  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  override def participantInterest(event: Event, participant: Person): (DomainContext) => Try[Boolean] = ???

  override def schedule(event: Event, location: Location, time: DateTime): DomainContext => Try[ScheduleItem] = {
    _ => Try(ScheduleItem(event, location, time, Seq.empty))
  }

  override def locationConfirm(scheduleItem: ScheduleItem): DomainContext => Try[ScheduleItem] = ???

  override def generalConfirm(scheduleItem: ScheduleItem): DomainContext => Confirmation = {
    _ => Confirmation(scheduleItem)
  }


  override def cancel(confirmation: Confirmation): DomainContext => Option[Event] = context => {
    val item = confirmation.scheduleItem
    val event = item.event

    val oldLocation = item.location
    val interval = new Interval(item.time, event.duration)

    item.copy(location =
      oldLocation.copy(
        books =
          oldLocation.books filterNot (_ == Booking(interval, event))
      ))
    Option(event.copy(status = Cancelled))
  }


  override def run(confirmation: Confirmation): DomainContext => Event = ???

  override def possibleLocationsForEvent(event: Event): DomainContext => Seq[ScheduleItem] = ???

  override def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Person] = ???

}
