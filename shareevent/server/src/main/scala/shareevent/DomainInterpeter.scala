package shareevent

import org.joda.time.DateTime

import scala.util.Try

trait DomainInterpeter {

  type Event
  type Location
  type Person
  type Participant <: Person
  type Organizer <: Person

  def createEvent(organizer: Organizer,
                  title: String,
                  theme: String,
                  organizerCost: BigDecimal,
                  startSchedule: DateTime,
                  endSchedule: DateTime): Try[Event]





}
