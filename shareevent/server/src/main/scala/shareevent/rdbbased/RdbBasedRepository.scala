package shareevent.rdbbased

import cats.{Monad, ~>}
import shareevent.{FutureMonad, TryToFuture}
import shareevent.model.{Event, Location, Person}
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}
import shareevent.persistence.Repository

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class RdbBasedRepository extends Repository[Future] {

  implicit val ec: ExecutionContext = ExecutionContext.global

  override implicit val mMonad: Monad[Future] = new FutureMonad()
  override implicit val tryToMonad: ~>[Try, Future] = TryToFuture

  class RdbBasedDAO[K,T] extends DAO[K,T]
  {
    override def query[MT <: ObjectMeta[T, MT]](q: QueryExpression[MT]): Future[Seq[T]] = ???

    override def store(obj: T): Future[T] = ???

    override def retrieve(key: K): Future[Option[T]] = ???

    override def delete(key: K): Future[Boolean] = ???

    override def merge(instance: T): Future[T] = ???
  }

  override lazy val personDAO: DAO[String, Person] = ???
  override lazy val locationDAO: DAO[Location.Id, Location] = ???
  override lazy val eventsDAO: DAO[Event.Id, Event] = ???

}
