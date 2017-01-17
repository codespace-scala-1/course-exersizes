package shareevent

import org.joda.time.DateTime
import shareevent.model.Participant

import scala.concurrent.Future
import scala.util.Try


trait DomainContext {


  trait Repository {

    def storeParticipant(p: Participant): Try[Unit]

    def retrieveParticipant(login: String): Try[Option[Participant]]

    def delete(login: String): Try[Unit]

    def store[T](obj: T)

    def retrieve[K](key: K)

    def delete[T](obj: T)
  }

  val repository: Repository

  def currentTime: DateTime



}
