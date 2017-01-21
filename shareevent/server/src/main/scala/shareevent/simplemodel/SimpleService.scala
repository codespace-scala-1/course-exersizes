package shareevent.simplemodel

import org.joda.time.{DateTime, Duration => JodaDuration}
import shareevent.{DomainContext, DomainService}
import shareevent.model._

import scala.util.{Failure, Success, Try}
import org.joda.time.Interval
import shareevent.persistence.QueryDSL
import shareevent.persistence.Repository.Objects.location

import QueryDSL._

class SimpleService extends DomainService {


  def createEvent(organizer: Person, title: String, theme: String, organizerCost: Money, duration: JodaDuration,
                  scheduleWindow: JodaDuration, quantityOfParticipants: Int): DomainContext => Try[Event] = {
  ctx =>
    Try {
      require(organizer.role == Role.Organizer)
      Event(
        None,
        title = title,
        theme = theme,
        organizer = OrganizerTag.tag(organizer),
        organizerCost = organizerCost,
        status = EventStatus.Initial,
        created = ctx.currentTime,
        duration = duration,
        scheduleWindow = scheduleWindow,
        minParticipantsQuantity = quantityOfParticipants)
    } flatMap {
       ctx.repository.eventsDAO.store(_)
    }
  }

  override def createLocation(name: String, capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordinate: Coordinate,
                              costs: Money): DomainContext => Try[Location] = { ctx =>
      // TODO: add check that capacity > 0
      ctx.repository.locationDAO.store(
        Location(None, name, capacity, coordinate, Seq.empty)
      )
  }

  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  override def participantInterest(event: Event, participant: Person): (DomainContext) => Try[Boolean] = _ => Success(true)

  override def schedule(eventId: Event.Id, locationId: Location.Id, time: DateTime): DomainContext => Try[ScheduleItem] = {

    _ => Try(ScheduleItem(eventId, locationId, time, Seq.empty))
  }

  override def locationConfirm(scheduleItem: ScheduleItem): DomainContext => Try[ScheduleItem] = {
    ctx => Try {

      ctx.repository.locationDAO.retrieveExistent(scheduleItem.locationId.id) match {
        case Success(location) => {
          def isCrossTime(booking: Booking, time: DateTime): Boolean = {
            booking.time.contains(time)
          }
          val bookings = location.bookings
          val isBad = bookings.exists(booking => isCrossTime(booking, scheduleItem.time) && scheduleItem.eventId != booking.eventId)
          !isBad
        }
        case Failure(ex) => throw ex
      }
      // booking chaNGE STATUS , RETURN
    }
  }

  override def generalConfirm(scheduleItem: ScheduleItem): DomainContext => Confirmation = {
    _ => Confirmation(scheduleItem)
  }


  override def cancel(confirmation: Confirmation): DomainContext => Try[Event] =

    context => {

      val item = confirmation.scheduleItem
      val eventId = item.eventId

      for {
           event <- context.repository.eventsDAO.retrieveExistent(eventId.id)
           interval = new Interval(item.time, event.duration)
           location <- context.repository.locationDAO.retrieveExistent(item.locationId.id)
           changed = location.copy(bookings = location.bookings.filterNot( _ == Booking(interval,eventId)))
           savedLocation = context.repository.locationDAO.merge(changed)
           cancelledEvent = event.copy(status = EventStatus.Cancelled)
           savedCancelledEvent <- context.repository.eventsDAO.merge(cancelledEvent)
      } yield {
        savedCancelledEvent
      }

  }


  override def run(confirmation: Confirmation): DomainContext => Try[Event] =
  ctx => {
    for {event <- ctx.repository.eventsDAO.retrieveExistent(
                              confirmation.scheduleItem.eventId.id)
         changed = event.copy(status = EventStatus.Done)
         saved <- ctx.repository.eventsDAO.merge(changed)
    }
     yield saved
  }

  override def possibleLocationsForEvent(event: Event): DomainContext => Try[Seq[ScheduleItem]] =
  { implicit ctx =>
      import shareevent.persistence.Repository.Objects._
      for {
        eventId <- event.id.toTry
        locations <- ctx.repository.locationDAO.query(
          location.select where location.capacity >= event.minParticipantsQuantity
        )
      } yield {
         for{location <- locations
             time <- possibleTimesForLocation(location) } yield {
           ScheduleItem(eventId,location.id.get,time,Seq.empty)
         }
      }
  }

  override def possibleParticipantsInEvent(event: Event): DomainContext => Try[Seq[Person]] =
    implicit ctx => {
      import shareevent.persistence.Repository.Objects._

      for { participants <- ctx.repository.personDAO.query(person.select where person.role === Role.Participant)
             p <- participants
             isInteresting <- participantInterest(event, p) if isInteresting
          } yield p
    }

  def possibleTimesForLocation(l:Location)(implicit ctx:DomainContext):Seq[DateTime] = {
    l.id match {
      case Some(x) => ctx.repository.locationDAO.retrieve(x.id) match {
        case Success(v) => v match {
          case Some(location) =>
            val gaps = for {
              booking <- location.bookings
              booking2 <- location.bookings if booking2 != booking
              gap = booking.time.gap(booking2.time)
            } yield gap
            gaps.distinct.map(g => g.getStart)
          case None => Seq()
        }
        case Failure(e) => Seq()
      }
      case None => Seq()
    }
  }
}
