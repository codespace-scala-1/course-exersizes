package shareevent.simplemodel

import org.joda.time.DateTime
import org.joda.time.{Duration => JodaDuration}
import shareevent.{DomainContext, DomainService}
import shareevent.model._

import scala.util.{Success, Try}
import org.joda.time.Interval

class SimpleService extends DomainService {


  override def createEvent(organizer: Person, title: String, theme: String, organizerCost: Money, duration: JodaDuration, scheduleWindow: JodaDuration): DomainContext => Try[Event] = ???

  override def createLocation(capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordination: Coordinate, costs: Money): DomainContext => Try[Location] = ???

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


  override def cancel(confirmation: Confirmation): DomainContext => Try[Event] = context => {
    val item = confirmation.scheduleItem
    val event = item.event

    val oldLocation = item.location
    val interval = new Interval(item.time, event.duration)

    item.copy(location =
      oldLocation.copy(
        books =
          oldLocation.books filterNot (_ == Booking(interval, event))
      ))
    Success(event.copy(status = Cancelled))
  }


  override def run(confirmation: Confirmation): DomainContext => Event = ???

  override def possibleLocationsForEvent(event: Event): DomainContext => Seq[ScheduleItem] = ???

  override def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Person] = ???

}
