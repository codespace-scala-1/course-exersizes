package shareevent

import org.joda.time.DateTime
import scala.util.{Success, Try}

class InMemoryContext extends DomainContext {


  class InMemoryRepo extends Repository {

    private var participants = Set[Participant]()

    def storeParticipant(participant: Participant): Try[Unit] = {
      participants = participants + participant
      Success(())
    }

    def retrieveParticipant(login: String): Try[Option[Participant]] =
      Try(participants.find(_.login == login))

    def delete(login: String): Try[Unit] = {
      for {op <- retrieveParticipant(login)
           p <- checkExistence(op)
      }  yield {
        participants = participants - p
      }
    }
  }

  def checkExistence[T](op:Option[T]): Try[T] =
    op.toRight(new IllegalStateException("login not found")).toTry

  override val repository = new InMemoryRepo {}

  override def currentTime: DateTime = new DateTime()

}
