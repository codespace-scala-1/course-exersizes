package shareevent.persistence.inmem

import org.joda.time.DateTime
import shareevent.DomainContext
import shareevent.model.{Location, Person}
import shareevent.persistence.Repository

import scala.util.{Success, Try}

class InMemoryContext extends DomainContext {

  class InMemoryRepo extends Repository {

    import shareevent.persistence.Repository._

    private var participants = Set[Person]()

    def storeParticipant(participant: Person): Try[Unit] = {
      participants = participants + participant
      Success(())
    }

    def retrieveParticipant(login: String): Try[Option[Person]] =
      Try(participants.find(_.login == login))

    def deleteParticipant(login: String): Try[Boolean] = {
      for {op <- retrieveParticipant(login) } yield {
        op.foreach{ p =>
          participants = participants - p
        }
        op.isDefined
      }
    }

    override lazy val locationDAO: DAO[Long, Location] = ???

    //override def retrieveDao[K, T](): DAO[K, T] = ???
  }

  def checkExistence[T](op:Option[T]): Try[T] =
    op.toRight(new IllegalStateException("login not found")).toTry

  override val repository = new InMemoryRepo {}

  override def currentTime: DateTime = new DateTime()

}
