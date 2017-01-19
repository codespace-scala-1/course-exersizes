package shareevent.persistence

import shareevent.model.{Location, Person}

import scala.util.Try

trait Repository
{
  import Repository._

  //TODO: refactor
  def storeParticipant(p: Person): Try[Unit]

  //TODO: refactor
  def retrieveParticipant(login: String): Try[Option[Person]]

  def deleteParticipant(login:String): Try[Boolean]

  //def retrieveDao[K, T](): DAO[K,T]

  val locationDAO:  DAO[Long,Location]


}

object Repository
{

  trait DAO[K, T] {

    def store(obj: T): Try[T]

    def retrieve(key: K): Try[Option[T]]

    def delete(key: K): Try[Boolean]

    def merge(instance: T): Try[T]
  }


}