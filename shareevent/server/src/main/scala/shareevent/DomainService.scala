package shareevent

import org.joda.time.{DateTime, Duration => JodaDuration}
import shareevent.model._

import scala.concurrent.duration.Duration
import scala.util.Try

trait DomainService {


  case class ScheduleItem(event: Event,
                          location: Location,
                          time: DateTime,
                          participants: Seq[Person])

  case class Confirmation(scheduleItem: ScheduleItem)


  def createEvent(organizer: Person,
                  title: String,
                  theme: String,
                  organizerCost: Money = Money.zero,
                  duration: JodaDuration = JodaDuration.standardHours(2),
                  scheduleWindow: JodaDuration = JodaDuration.standardDays(14)
                 ): DomainContext => Try[Event]



  def createLocation(capacity: Int,
                     startSchedule: DateTime,
                     endSchedule: DateTime,
                     coordination: Coordinate,
                     costs: Money): DomainContext => Try[Location]


  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  def participantInterest(event:Event, participant: Person): (DomainContext) => Try[Boolean]

  def schedule(event: Event, location: Location, time: DateTime): DomainContext => Try[ScheduleItem]

  def locationConfirm(scheduleItem: ScheduleItem): DomainContext => Try[ScheduleItem]

  def generalConfirm(scheduleItem: ScheduleItem): DomainContext => Confirmation

  def cancel(confirmation: Confirmation): DomainContext => Try[Boolean]

  def run(confirmation: Confirmation): DomainContext => Event

  def possibleLocationsForEvent(event:Event): DomainContext => Seq[ScheduleItem]

  def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Person]


}
