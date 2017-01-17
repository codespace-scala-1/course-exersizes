package shareevent.model

import org.joda.time.{DateTime, Interval, Duration => JodaDuration}

case class Event(
                title: String,
                theme: String,
                organizer: Person,
                organizerCost: Money,
                status: EventStatus,
                created: DateTime,
                duration: JodaDuration,
                scheduleWindow: JodaDuration
                )

case class ScheduleItem(event: Event,
                        location: Location,
                        time: DateTime,
                        participants: Seq[Person])
{

  lazy val interval = new Interval(time,event.duration)

}

case class Confirmation(scheduleItem: ScheduleItem)
