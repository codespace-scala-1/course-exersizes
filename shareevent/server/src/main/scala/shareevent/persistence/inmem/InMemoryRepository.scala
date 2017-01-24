package shareevent.persistence.inmem

import org.joda.time.DateTime
import shareevent.DomainContext
import shareevent.model.{Event, Location, Person}
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}
import shareevent.persistence.Repository

import scala.util.{Success, Try}

class InMemoryContext extends DomainContext {

  class InMemoryRepo extends Repository {

    import shareevent.persistence.Repository._

    override val personDAO: DAO[String,Person] = new DAO[String,Person] {

      private var participants = Map[String,Person]()

      def store(obj: Person): Try[Person] =
      {
        participants = participants.updated(obj.login,obj)
        Success(obj)
      }

      def retrieve(key: String): Try[Option[Person]] =
      {
        Success(participants.get(key))
      }

      def delete(key: String): Try[Boolean] =
      {
        val oldParticipants = participants
        participants = participants - key
        Success(participants.size != oldParticipants.size)
      }

      def merge(instance: Person): Try[Person] =
      {
        val r = participants.get(instance.login) map { p =>
          participants = participants.updated(instance.login,instance)
          instance
        }
        r.toRight(new IllegalArgumentException(s"instance ${instance.login} is not in our collection")).toTry
      }

      override def query[MT <: ObjectMeta[Person, MT]](q: QueryExpression[MT])(implicit mt: ObjectMeta[Person, MT]): Try[Seq[Person]] = ???
    }


    override lazy val locationDAO: DAO[Long, Location] = ???

    override lazy val eventsDAO: DAO[Long, Event ] = ???

    //override def retrieveDao[K, T](): DAO[K, T] = ???
  }

  def checkExistence[T](op:Option[T]): Try[T] =
    op.toRight(new IllegalStateException("login not found")).toTry

  override val repository = new InMemoryRepo {}

  override def currentTime: DateTime = new DateTime()

}
