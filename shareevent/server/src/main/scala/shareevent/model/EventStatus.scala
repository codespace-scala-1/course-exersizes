package shareevent.model

sealed trait EventStatus
case object Initial extends EventStatus
case object PreBooked extends EventStatus
case object Booked   extends EventStatus
case object Cancelled extends EventStatus
case object Done      extends EventStatus
