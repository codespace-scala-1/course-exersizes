package shareevent

import org.joda.time.DateTime

import scala.util.Try

trait DomainInterpeter[Event, Location, Participant, Organizer] {


  def createEvent(organizer: Organizer,
                  title: String,
                  theme: String,
                  startSchedule: DateTime,
                  endSchedule: DateTime): Try[Event]




}
