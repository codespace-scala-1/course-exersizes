package shareevent

import scala.concurrent.Future
import scala.util.Try

trait DomainRepository[Participant] {

  def storeParticipant(p:Participant): Try[Unit]

  def retrieveParticipant(login: String): Try[Option[Participant]]

}
