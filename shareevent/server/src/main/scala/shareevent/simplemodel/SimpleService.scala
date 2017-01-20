package shareevent.simplemodel

import org.joda.time.{DateTime, Duration => JodaDuration}
import shareevent.{DomainContext, DomainService}
import shareevent.model._

import scala.util.{Failure, Success, Try}
import org.joda.time.Interval

class SimpleService extends DomainService {


  def createEvent(organizer: Person, title: String, theme: String, organizerCost: Money, duration: JodaDuration,
                  scheduleWindow: JodaDuration, quantityOfParticipants: Int): DomainContext => Try[Event] = {
  ctx =>
    Try {
      require(organizer.role == Role.Organizer)
      Event(
        NoId,
        title = title,
        theme = theme,
        organizer = organizer,
        organizerCost = organizerCost,
        status = EventStatus.Initial,
        created = ctx.currentTime,
        duration = duration,
        scheduleWindow = scheduleWindow,
        minQuantityParticipants = quantityOfParticipants)
    } flatMap {
       ctx.repository.eventsDAO.store(_)
    }
  }

  override def createLocation(name: String, capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordinate: Coordinate,
                              costs: Money): DomainContext => Try[Location] = { ctx =>
      // TODO: add check that capacity > 0
      ctx.repository.locationDAO.store(
        Location(NoId, name, capacity, coordinate, Seq.empty)
      )
  }

  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  override def participantInterest(event: Event, participant: Person): (DomainContext) => Try[Boolean] = _ => Success(true)

  override def schedule(event: Event, locationId: Id[Long,Location], time: DateTime): DomainContext => Try[ScheduleItem] = {

    _ => Try(ScheduleItem(event, locationId, time, Seq.empty))
  }

  override def locationConfirm(scheduleItem: ScheduleItem): DomainContext => Try[ScheduleItem] = {
    //ctx =>
      //scheduleItem.location
    ???
  }

  override def generalConfirm(scheduleItem: ScheduleItem): DomainContext => Confirmation = {
    _ => Confirmation(scheduleItem)
  }


  override def cancel(confirmation: Confirmation): DomainContext => Try[Event] =

    context => {

      val item = confirmation.scheduleItem
      val event = item.event
      val interval = new Interval(item.time, event.duration)

      for {optLocation <- context.repository.locationDAO.retrieve(item.locationId.id)
           location <- optLocation.toRight(new Exception("Can't find location")).toTry
           changed = location.copy(bookings = location.bookings.filterNot( _ == Booking(interval,event)))
           savedLocation = context.repository.locationDAO.merge(changed)
           cancelledEvent = event.copy(status = EventStatus.Cancelled)
           savedCancelledEvent <- context.repository.eventsDAO.merge(cancelledEvent)
      } yield {
        savedCancelledEvent
      }

  }


  override def run(confirmation: Confirmation): DomainContext => Event = ???

  override def possibleLocationsForEvent(event: Event): DomainContext => Seq[ScheduleItem] = ???

  override def possibleParticipantsInEvent(event: Event): DomainContext => Seq[Person] = ???



}
