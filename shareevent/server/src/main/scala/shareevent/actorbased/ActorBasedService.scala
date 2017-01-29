package shareevent.actorbased

import akka.actor.ActorSystem
import org.joda.time.{DateTime, Duration}
import shareevent.{DomainContext, DomainService}
import shareevent.model.{Confirmation, Coordinate, Event, Location, Money, Person, ScheduleItem}

import scala.concurrent.Future

class ActorBasedService(implicit val actorSystem:ActorSystem) extends DomainService[Future] {


  override def createEvent(organizerId: Person.Id, title: String, theme: String, organizerCost: Money, duration: Duration, scheduleWindow: Duration, quantityOfParticipants: Int): (DomainContext[Future]) => Future[Event] = ???

  override def createLocation(name: String, capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordination: Coordinate, costs: Money): (DomainContext[Future]) => Future[Location] = ???

  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  override def participantInterest(event: Event, participant: Person): (DomainContext[Future]) => Future[Boolean] = ???

  override def schedule(eventId: Event.Id, locationId: Location.Id, time: DateTime): (DomainContext[Future]) => Future[ScheduleItem] = ???

  override def locationConfirm(scheduleItem: ScheduleItem): (DomainContext[Future]) => Future[ScheduleItem] = ???

  override def generalConfirm(scheduleItem: ScheduleItem): (DomainContext[Future]) => Confirmation = ???

  override def cancel(confirmation: Confirmation): (DomainContext[Future]) => Future[Event] = ???

  override def run(confirmation: Confirmation): (DomainContext[Future]) => Future[Event] = ???

  override def possibleLocationsForEvent(event: Event): (DomainContext[Future]) => Future[Seq[ScheduleItem]] = ???

  override def possibleParticipantsInEvent(event: Event): (DomainContext[Future]) => Future[Seq[Person]] = ???
}
