package shareevent

import org.joda.time.DateTime
import shareevent.simplemodel.SParticipant

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Try}

class InMemoryContext extends DomainContext[SParticipant] {

  class InMemoryRepo extends Repository {

    private var participants = Set[SParticipant]()

    def storeParticipant(participant: SParticipant): Try[Unit] = {
      participants = participants + participant
      Success(())
    }

    def retrieveParticipant(login: String): Try[Option[SParticipant]] =
      Try(participants.find(_.login == login))

    def delete(login: String): Try[Unit] = {
      retrieveParticipant(login) map {
        case Some(participant) => participants = participants - participant
      }
      Success(())
    }
  }

  override val repository = new InMemoryRepo {}

  override def currentTime: DateTime = new DateTime()

}
