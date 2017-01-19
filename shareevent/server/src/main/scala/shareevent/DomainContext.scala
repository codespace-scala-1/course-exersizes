package shareevent

import org.joda.time.DateTime
import shareevent.persistence._
import shareevent.model.{Location, Person}

import scala.util.Try


trait DomainContext {

  val repository: Repository

  def currentTime: DateTime

}
