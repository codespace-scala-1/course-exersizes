package shareevent.model

import org.joda.time.{DateTime, Interval, Duration => JodaDuration}

case class Event(
                  id: Option[Event.Id],
                  title: String,
                  theme: String,
                  organizerId: Person.Id ,
                  organizerCost: Money,
                  status: EventStatus,
                  created: DateTime,
                  duration: JodaDuration,
                  scheduleWindow: JodaDuration,
                  minParticipantsQuantity: Int = 5
                ) {
}

object Event
{
  type Id = PersistenceId[Long,Event]

  def id(l:Long) = new Id(l)
}


case class ScheduleItem(eventId: Event.Id,
                        locationId: Location.Id,
                        time: DateTime,
                        participants: Seq[Person.Id]) {
}

case class Confirmation(scheduleItem: ScheduleItem)