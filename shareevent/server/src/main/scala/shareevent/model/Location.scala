package shareevent.model

import org.joda.time.Interval

sealed trait BookingStatus

case object BookingStatus {

  case object Preliminary extends BookingStatus

  case object Final extends BookingStatus

}

case class Booking(time: Interval,
                    eventId: Event.Id,
                    status: BookingStatus = BookingStatus.Preliminary)

case class Location(
                     id: Option[Location.Id],
                     name:  String,
                     capacity:  Int,
                     coordinate: Coordinate,
                     bookings: Seq[Booking] = Seq()
                   )

object Location
{

  type Id = PersistenceId[Long,Location]

  def id(l:Long) = new Id(l)

}