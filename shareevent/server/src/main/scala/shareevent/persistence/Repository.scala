package shareevent.persistence

import shareevent.model.{Location, Person}

import scala.util.Try

trait Repository
{
  import Repository._


  //def retrieveDao[K, T](): DAO[K,T]

  val personDAO: DAO[String,Person]

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