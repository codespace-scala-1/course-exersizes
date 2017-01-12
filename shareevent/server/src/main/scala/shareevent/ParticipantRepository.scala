package shareevent

import shareevent.simplemodel.SParticipant

import scala.concurrent.{ExecutionContext, Future}

class InMemoryParticipantRepository(implicit execCtx: ExecutionContext) extends Repository[SParticipant, String] {
  var participants = Set[SParticipant]()

  def store(entity: SParticipant) = {
    participants = participants + entity
    Future()
  }

  def retrieve(id: String) = Future(participants.find(_.login == id))
}
