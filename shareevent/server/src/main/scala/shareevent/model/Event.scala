package shareevent.model

import org.joda.time.{DateTime, Interval, Duration => JodaDuration}

case class Event(
                id: Option[Long],
                title: String,
                theme: String,
                organizer: Person,
                organizerCost: Money,
                status: EventStatus,
                created: DateTime,
                duration: JodaDuration,
                scheduleWindow: JodaDuration,
                minQuantityParticipants: Int = 5
                ) {
  val minParticipantsQuantity: Int = minQuantityParticipants
}

object Event
{
  class Id(val value: Long) extends AnyVal

}

case class ScheduleItem(event: Event,
                        locationId: Long,
                        time: DateTime,
                        participants: Seq[Person]) {
  lazy val interval = new Interval(time, event.duration)
}

case class Confirmation(scheduleItem: ScheduleItem)