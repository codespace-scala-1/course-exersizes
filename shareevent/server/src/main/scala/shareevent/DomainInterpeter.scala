package shareevent

import org.joda.time.{DateTime, Duration => JodaDuration}
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
                  organizerCost: Money = implicitly[Numeric[Money]].zero,
                  duration: JodaDuration = JodaDuration.standardHours(2),
                  scheduleWindow: JodaDuration = JodaDuration.standardDays(14)
                 ): DomainContext[Participant] => Try[Event]



  def createLocation(capacity: Int,
                     startSchedule: DateTime,
                     endSchedule: DateTime,
                     coordination: Coordinate,
                     costs: Money): DomainContext[Participant] => Try[Location]


  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  def participantInterest(event:Event, participant: Participant): DomainContext[Participant] => Boolean

  def schedule(event: Event, location: Location, time: DateTime, cost: Money): DomainContext[Participant] => Try[ScheduleItem]

  def locationConfirm(scheduleItem: ScheduleItem): DomainContext[Participant] => Try[ScheduleItem]

  def generalConfirm(scheduleItem: ScheduleItem): DomainContext[Participant] => Confirmation

  def cancel(confirmation: Confirmation): DomainContext[Participant] => Try[Boolean]

  def run(confirmation: Confirmation): DomainContext[Participant] => Event

  def status(event:Event): EventStatus

  def possibleLocationsForEvent(event:Event): DomainContext[Participant] => Seq[ScheduleItem]

  def possibleParticipantsInEvent(event: Event): DomainContext[Participant] => Seq[Participant]

  def bestScheduleLocations(scheduleItems: Seq[ScheduleItem]): DomainContext[Participant] => Try[ScheduleItem] = ???

  def runEvent(organizer: Organizer, title: String): DomainContext[Participant] => Try[ScheduleItem] =
  repo => {
    for {event <- createEvent(organizer, title, theme = "*")(repo)
         scheduledItems = possibleLocationsForEvent(event)(repo)
         scheduledItem <- bestScheduleLocations(scheduledItems)(repo)
    } yield {
        scheduledItem
    }
  }

}
