package shareevent

import org.joda.time.{DateTime, Duration => JodaDuration}
import shareevent.model._

import scala.util.Try

trait DomainService {


  def createEvent(organizer: Person,
                  title: String,
                  theme: String,
                  organizerCost: Money = Money.zero,
                  duration: JodaDuration = JodaDuration.standardHours(2),
                  scheduleWindow: JodaDuration = JodaDuration.standardDays(14),
                  quantityOfParticipants: Int = 5
                 ): DomainContext => Try[Event]


  def createLocation(name: String,
                     capacity: Int,
                     startSchedule: DateTime,
                     endSchedule: DateTime,
                     coordination: Coordinate,
                     costs: Money): DomainContext => Try[Location]


  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  def participantInterest(event:Event, participant: Person): (DomainContext) => Try[Boolean]

  def schedule(eventId: Event.Id, locationId: PersistenceId[Long,Location], time: DateTime): DomainContext => Try[ScheduleItem]

  def locationConfirm(scheduleItem: ScheduleItem): DomainContext => Try[ScheduleItem]

  def generalConfirm(scheduleItem: ScheduleItem): DomainContext => Confirmation

  def cancel(confirmation: Confirmation): DomainContext => Try[Event]

  def run(confirmation: Confirmation): DomainContext => Event

  def possibleLocationsForEvent(event:Event): DomainContext => Try[Seq[ScheduleItem]]

  def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Person]


}
