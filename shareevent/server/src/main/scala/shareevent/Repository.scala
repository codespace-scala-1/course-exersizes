package shareevent

import org.joda.time.DateTime
import shareevent.model.{Location, Person}

import scala.util.Try


trait DomainContext {

  type Participant = Person

  trait Repository {

    //TODO: refactor
    def storeParticipant(p: Participant): Try[Unit]

    //TODO: refactor
    def retrieveParticipant(login: String): Try[Option[Participant]]

    def deleteParticipant(login:String): Try[Boolean]

    def retrieveDao[K, T](): DAO[K,T] = ???

    trait DAO[K,T] {

      def store(obj: T): Try[T]

      def retrieve(key: K): Try[Option[T]]

      def delete(key: K): Try[Boolean]

      def merge(instance: T): Try[T]
    }

    lazy val locationDAO: DAO[Long, Location] = ???

  }

  val repository: Repository

  def currentTime: DateTime

}
