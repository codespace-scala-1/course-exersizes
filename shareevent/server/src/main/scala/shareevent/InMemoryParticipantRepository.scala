package shareevent

import shareevent.simplemodel.SParticipant

import scala.concurrent.Future

class InMemoryParticipantRepository extends Repository[SParticipant, String] {

  var participants = Seq[SParticipant]()

  def store(entity: SParticipant) = {
    participants = participants :+ entity
    Future()
  }

  def retrieve(id: String): Future[Option[SParticipant]] = Future(participants.find(_.login == id))
}
