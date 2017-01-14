package shareevent.simplemodel

import org.joda.time.DateTime
import org.joda.time.{Duration=>JodaDuration}
import shareevent.{DomainInterpeter, DomainRepository}
import shareevent.model.{Coordinate, EventStatus}


import scala.util.Try

class SEvent

class SLocation

trait SPerson {
  def login: String

  def password: String

  def email: Option[String]

  def slackId: Option[String]

  def phoneNo: Option[String]
}

case class SOrganizer( login: String,
                       password: String,
                       email: Option[String] = None,
                       slackId: Option[String] = None,
                       phoneNo: Option[String] = None) extends  SPerson

case class SParticipant( login: String,
                       password: String,
                       email: Option[String] = None,
                       slackId: Option[String] = None,
                       phoneNo: Option[String] = None) extends  SPerson


class Interpreter extends DomainInterpeter {

  override type Event = SEvent
  override type Location = SLocation
  override type Person = SPerson
  override type Participant = SParticipant
  override type Organizer = SOrganizer
  override type Money = BigDecimal

  implicit val moneyNumeric = implicitly[Numeric[BigDecimal]]

  override def createEvent(organizer: SOrganizer, title: String, theme: String, organizerCost: Money, duration: JodaDuration, startPossibleSchedule: DateTime, endPossibleSchedule: DateTime): DomainRepository[SParticipant] => Try[SEvent] = ???

  override def createLocation(capacity: Int, startSchedule: DateTime, endSchedule: DateTime, coordination: Coordinate, costs: Money): DomainRepository[SParticipant] => Try[SLocation] = ???

  /**
    * If participant is interested in event, he can participate
    * in scheduling of one.
    */
  override def participantInterest(event: SEvent, participant: SParticipant): DomainRepository[SParticipant] => Boolean = ???

  override def schedule(event: SEvent, location: SLocation, time: DateTime, cost: Money):DomainRepository[SParticipant] => Try[ScheduleItem] = ???

  override def locationConfirm(scheduleItem: ScheduleItem): DomainRepository[SParticipant] => Try[ScheduleItem] = ???

  override def generalConfirm(scheduleItem: ScheduleItem): DomainRepository[SParticipant] =>Confirmation = ???

  override def cancel(confirmation: Confirmation): DomainRepository[SParticipant] =>Try[Boolean] = ???

  override def run(confirmation: Confirmation): DomainRepository[SParticipant] =>SEvent = ???

  override def status(event: SEvent): EventStatus = ???

  override def possibleLocationsForEvent(event: SEvent): DomainRepository[SParticipant] => Seq[ScheduleItem] = ???

  override def possibleParticipantsInEvent(event: SEvent): DomainRepository[SParticipant] => Seq[SParticipant] = ???
}
