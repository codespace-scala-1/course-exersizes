package shareevent

import org.joda.time.DateTime
import shareevent.model.{Coordinate, EventStatus}

import scala.concurrent.duration.Duration
import scala.util.Try

//TODO:
// Missing operation: from event to sequence of sheduled item.
//  (set of location ?)
// Missions operation: is particpant agree with sheduled item
// Missing operation: how we select participants for event.
trait DomainInterpeter {

  type Event

  type Location
  type Person
  type Participant <: Person
  type Organizer <: Person

  type Money <: Number

  case class ScheduleItem(event: Event,
                          location: Location,
                          time: DateTime,
                          participants: Seq[Participant],
                          locationConfirm: Boolean)

  case class Confirmation(scheduleItem: ScheduleItem)


  def createEvent(organizer: Organizer,
                  title: String,
                  theme: String,
                  organizerCost: Money,
                  duration: Duration,
                  startPossibleSchedule: DateTime,
                  endPossibleSchedule: DateTime): Try[Event]



  def createLocation(capacity: Int,
                     startSchedule: DateTime,
                     endSchedule: DateTime,
                     coordination: Coordinate,
                     costs: Money): Try[Location]


  /**
    * If participant is interest in event, then it can participate
    * in scheduling of one.
    */
  def participantInterest(event:Event,participant: Participant):Boolean

  def schedule(event: Event, location: Location, time: DateTime, cost: Money): Try[ScheduleItem]

  def locationConfirm(scheduleItem: ScheduleItem):ScheduleItem

  def generalConfirm(scheduleItem: ScheduleItem): Confirmation

  def cancel(confirmation: Confirmation): Try[Boolean]

  def run(confirmation: Confirmation): Event

  def status(event:Event): EventStatus

  def possibleLocationsForEvent(event:Event): Seq[ScheduleItem]

  def possibleParticipantsInEvent(event: Event): Seq[Participant]


}
