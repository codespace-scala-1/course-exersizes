package shareevent

import org.joda.time.DateTime
import shareevent.model.{Person}

import scala.concurrent.Future
import scala.util.Try


trait DomainContext {

  type Participant = Person

  trait Repository {

    //TODO: refactor
    def storeParticipant(p: Participant): Try[Unit]

    //TODO: refactor
    def retrieveParticipant(login: String): Try[Option[Participant]]

    def deleteParticipant(login:String): Try[Boolean]

    trait DAO[K,T] {

      def store(obj: T): Try[T]

      def retrieve(key: K): Try[Option[T]]

      def delete(key: K): Try[Boolean]
    }

  }

  val repository: Repository

  def currentTime: DateTime



}
