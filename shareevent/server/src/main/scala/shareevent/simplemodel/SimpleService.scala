package shareevent.simplemodel

import org.joda.time.{Duration => JodaDuration, DateTime}
import shareevent.{DomainContext, DomainService}
import shareevent.model._

import scala.util.Try


class SimpleService extends DomainService {


  override def createEvent(organizer: Person, title: String, theme: String, organizerCost: Money, duration: JodaDuration, scheduleWindow: JodaDuration): DomainContext => Try[Event] = ???

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

  override def generalConfirm(scheduleItem: ScheduleItem): DomainContext =>Confirmation =
    { _ => Confirmation(scheduleItem) }



  override def cancel(confirmation: Confirmation): DomainContext => Option[Event] = context => {
    val item = confirmation.scheduleItem
    ???
    //context.repository.delete(item.location)
    Option(item.event.copy(status = Cancelled))
  }


  override def run(confirmation: Confirmation): DomainContext => Event = ???

  override def possibleLocationsForEvent(event: Event): DomainContext => Seq[ScheduleItem] = ???

  override def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Person] = ???

}
