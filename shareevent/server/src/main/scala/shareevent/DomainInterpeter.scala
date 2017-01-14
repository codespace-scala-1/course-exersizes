package shareevent

import org.joda.time.DateTime
import org.joda.time.{Duration => JodaDuration}
import shareevent.model.{Coordinate, EventStatus}

import scala.concurrent.duration.Duration
import scala.util.Try

//TODO:
// Missing operation: from event to sequence of scheduled item.
//  (set of location ?)
// Missions operation: does participant agree with scheduled item
// Missing operation: how we select participants for event.
trait DomainInterpeter {
  type Event

  type Location
  type Person
  type Participant <: Person
  type Organizer <: Person

  type Money <: Number

  implicit val moneyNumeric: Numeric[Money]


  case class ScheduleItem(event: Event,
                          location: Location,
                          time: DateTime,
                          participants: Seq[Participant])

  case class Confirmation(scheduleItem: ScheduleItem)


  def createEvent(organizer: Organizer,
                  title: String,
                  theme: String,
                  organizerCost: Money,
                  duration: JodaDuration,
                  startPossibleSchedule: DateTime,
                  endPossibleSchedule: DateTime): DomainRepository[Participant] => Try[Event]



  def createLocation(capacity: Int,
                     startSchedule: DateTime,
                     endSchedule: DateTime,
                     coordination: Coordinate,
                     costs: Money): DomainRepository[Participant] => Try[Location]


  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  def participantInterest(event:Event, participant: Participant): DomainRepository[Participant] => Boolean

  def schedule(event: Event, location: Location, time: DateTime, cost: Money): DomainRepository[Participant] => Try[ScheduleItem]

  def locationConfirm(scheduleItem: ScheduleItem): DomainRepository[Participant] => Try[ScheduleItem]

  def generalConfirm(scheduleItem: ScheduleItem): DomainRepository[Participant] => Confirmation

  def cancel(confirmation: Confirmation): DomainRepository[Participant] => Try[Boolean]

  def run(confirmation: Confirmation): DomainRepository[Participant] => Event

  def status(event:Event): EventStatus

  def possibleLocationsForEvent(event:Event): DomainRepository[Participant] => Seq[ScheduleItem]

  def possibleParticipantsInEvent(event: Event): DomainRepository[Participant] => Seq[Participant]

  def bestScheduleLocations(scheduleItems: Seq[ScheduleItem]): DomainRepository[Participant] => Try[ScheduleItem] = ???

  def runEvent(organizer: Organizer, title: String): DomainRepository[Participant] => Try[ScheduleItem] =
  repo => {
    for {event <- createEvent(organizer, title, theme = "*",
        implicitly[Numeric[Money]].zero,
        duration = JodaDuration.standardHours(2),
        startPossibleSchedule = new DateTime().plusDays(1),
        endPossibleSchedule = new DateTime().plusDays(14))(repo)
         scheduledItems = possibleLocationsForEvent(event)(repo)
         scheduledItem <- bestScheduleLocations(scheduledItems)(repo)
    } yield {
        scheduledItem
    }
  }

}
