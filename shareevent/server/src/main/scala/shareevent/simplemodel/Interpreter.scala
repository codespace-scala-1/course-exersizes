package shareevent.simplemodel

import org.joda.time.DateTime
import shareevent.DomainInterpeter

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


abstract class Interpreter extends DomainInterpeter {

  override type Event = SEvent
  override type Location = SLocation
  override type Person = SPerson
  override type Participant = SParticipant
  override type Organizer = SOrganizer



}
