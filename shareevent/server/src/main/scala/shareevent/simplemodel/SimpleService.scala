package shareevent.simplemodel

import org.joda.time.DateTime
import org.joda.time.{Duration => JodaDuration}
import shareevent.{DomainContext, DomainService}
import shareevent.model._

import scala.util.Try



class SimpleService extends DomainService {


  override def createEvent(organizer: Organizer, title: String, theme: String, organizerCost: Money, duration: JodaDuration, scheduleWindow: JodaDuration): DomainContext => Try[Event] = ???

  override def createLocation(capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordination: Coordinate, costs: Money): DomainContext => Try[Location] = ???

  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  override def participantInterest(event: Event, participant: Participant): DomainContext => Boolean = ???

  override def schedule(event: Event, location: Location, time: DateTime, cost: Money):DomainContext => Try[ScheduleItem] = ???

  override def locationConfirm(scheduleItem: ScheduleItem): DomainContext => Try[ScheduleItem] = ???

  override def generalConfirm(scheduleItem: ScheduleItem): DomainContext =>Confirmation = ???

  override def cancel(confirmation: Confirmation): DomainContext =>Try[Boolean] = ???

  override def run(confirmation: Confirmation): DomainContext => Event = ???

  override def possibleLocationsForEvent(event: Event): DomainContext => Seq[ScheduleItem] = ???

  override def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Participant] = ???
}
