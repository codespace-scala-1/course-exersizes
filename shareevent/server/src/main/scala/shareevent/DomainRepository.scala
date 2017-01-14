package shareevent

import shareevent.simplemodel.SParticipant

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

class InMemoryRepository extends DomainRepository[SParticipant] {

  private var participants = Set[SParticipant]()

  def storeParticipant(participant: SParticipant):Try[Unit] = {
    participants = participants + participant
    Success(())
  }

  def retrieveParticipant(login: String) =
     Try(participants.find(_.login == login))

}
