package shareevent.model

import org.joda.time.DateTime
import org.joda.time.{Duration=>JodaDuration}

case class Event(
                title: String,
                theme: String,
                organizer: Person,
                organizerCost: Money,
                status: EventStatus,
                created: DateTime,
                duration: JodaDuration,
                minQuantityParticipants: Int = 5
                ) {
  val minParticipantsQuantity: Int = minQuantityParticipants
}
