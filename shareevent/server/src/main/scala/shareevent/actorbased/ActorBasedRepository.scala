package shareevent.actorbased

import akka.actor.{Actor, ActorSystem}
import akka.pattern.ask
import akka.persistence.PersistentActor
import akka.util.Timeout
import cats.Monad
import shareevent.model.{Event, Location, Person}
import shareevent.model.Location.Id
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}
import shareevent.persistence.Repository

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

sealed trait RepositoryMessage

case class Store[T](x:T) extends RepositoryMessage
case object Retrieve extends RepositoryMessage

import shareevent._

class ActorBasedRepository(implicit val actorSystem:ActorSystem ) extends Repository[Future] {


  implicit val timeout = Timeout(30 seconds)
  implicit val executionContext = actorSystem.dispatcher

  implicit val mMonad: cats.Monad[Future] = new FutureMonad
  implicit val tryToMonad = shareevent.TryToFuture

  abstract class ActorBasedDAO[K,T](val pathName:String) extends DAO[K,T] {

    override def query[MT <: ObjectMeta[T, MT]](q: QueryExpression[MT]): Future[Seq[T]] = ???


    override def store(obj: T): Future[T]


    override def retrieve(key: K): Future[Option[T]]

    override def delete(key: K): Future[Boolean] = ???

    override def merge(instance: T): Future[T] = ???
  }



  val locationDAO = new ActorBasedDAO[Location.Id,Location]("locations") {


    override def store(obj: Location): Future[Location] = {
       if (obj.id.isEmpty) {
         for{idv <- (IdGenerator.createOrFind(pathName) ? NextId).mapTo[NextIdReply]
             id = Location.id(idv.id)
             ref = LocationActor.createOrFind(id,obj.name)
             location <- (ref ? Store(obj)).mapTo[Location]
             } yield  location
       } else {
         Future successful obj
       }
    }

    override def retrieve(key: Id): Future[Option[Location]] = {
      val locationFuture = for{
        ref <- LocationActor.find(key)
        obj <- (ref ? Retrieve).mapTo[Location]
      } yield Some(obj)

      locationFuture.recover{case ex => None}

    }
  }


  override lazy val personDAO: DAO[String, Person] = ???

  override lazy val eventsDAO: DAO[Event.Id, Event] = ???


}
