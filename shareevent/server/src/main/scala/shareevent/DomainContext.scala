package shareevent

import org.joda.time.DateTime
import shareevent.persistence._
import shareevent.model.{Location, Person}

import scala.language.higherKinds


trait DomainContext[M[_]] {

  val repository: Repository[M]

  def currentTime: DateTime

}
