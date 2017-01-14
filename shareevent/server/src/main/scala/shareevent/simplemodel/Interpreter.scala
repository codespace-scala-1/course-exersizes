package shareevent.simplemodel

import org.joda.time.DateTime
import shareevent.DomainInterpeter

import scala.util.Try

class SEvent

class SLocation

class SPerson

class SOrganizer extends  SPerson

abstract class Interpreter extends DomainInterpeter {

  override type Event = SEvent
  override type Location = SLocation
  override type Person = SPerson
  override type Participant = SPerson
  override type Organizer = SOrganizer



}
