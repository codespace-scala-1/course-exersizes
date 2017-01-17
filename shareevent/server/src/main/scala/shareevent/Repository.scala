package shareevent

import org.joda.time.DateTime

import scala.concurrent.Future
import scala.util.Try


trait DomainContext[Participant] {


  trait Repository {

    def storeParticipant(p: Participant): Try[Unit]

    def retrieveParticipant(login: String): Try[Option[Participant]]

    def delete(login: String): Try[Unit]

  }

  val repository: Repository

  def currentTime: DateTime

}
