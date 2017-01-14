package shareevent.simplemodel

import shareevent.DomainInterpeter

class SEvent
class SLocation
trait SPerson {

  def login: String

  def password: String

  def email: Option[String]

  def slackId: Option[String]

  def phone: Option[String]

}

case class SOrganizer(login: String,
                      password: String,
                      email: Option[String] = None,
                      slackId: Option[String] = None,
                      phone: Option[String] = None) extends SPerson(login: Option[String])

case class SParticipant(login: String,
                        password: String,
                        email: Option[String] = None,
                        slackId: Option[String] = None,
                        phone: Option[String] = None) extends SPerson

abstract class Interpreter extends DomainInterpeter {

  override type Event = SEvent
  override type Location = SLocation
  override type Person = SPerson
  override type Participant = SPerson
  override type Organizer = SOrganizer

}
