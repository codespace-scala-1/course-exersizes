package shareevent.persistence

import cats.{Monad, ~>}
import cats.syntax.all._
import org.joda.time.Interval
import shareevent.model.Role.Role
import shareevent.model._
import shareevent.persistence.QueryDSL.ObjectMeta

import scala.util.{Failure, Success, Try}
import scala.language.higherKinds

trait Repository[M[_]]
{
  import Repository._

  implicit val mMonad: Monad[M]
  implicit val tryToMonad: Try ~> M


  trait DAO[K, T] {


    def query[MT<:ObjectMeta[T,MT]](q: QueryDSL.QueryExpression[MT])(implicit mt:ObjectMeta[T,MT]): M[Seq[T]]

    def store(obj: T): M[T]

    def retrieve(key: K): M[Option[T]]

    def retrieveExistent(key:K): M[T] = {
      retrieve(key).flatMap { opt =>
        val t: Try[T] = opt match {
          case Some(o) => Success(o)
          case None => Failure(new IllegalStateException("pk not found"))
        }
        tryToMonad(t)
      }

    }

    def delete(key: K): M[Boolean]

    def merge(instance: T): M[T]
  }


  //def retrieveDao[K, T](): DAO[K,T]

  val personDAO: DAO[String,Person]


  val locationDAO:  DAO[Long,Location]


  val eventsDAO: DAO[Long,Event]


}

object Repository
{

  // TODO: implement via macroses and/or shapeless.
  object Objects {

    import QueryDSL._



    class PersonMeta extends ObjectMeta[Person,PersonMeta]("person") {
      val login = field[String]("login")
      val password = field[String]("password")
      val role = field[Role]("role")
      val email = field[Option[String]]("email")
      val slackId = field[Option[String]]("slackId")
      val phoneNo = field[Option[String]]("phoneNo")
    }

    implicit val person: PersonMeta = new PersonMeta()

    /*case class Booking(time: Interval,
                       event: Event,
                       status: BookingStatus = BookingStatus.Preliminary)
                       */


    class LocationMeta extends ObjectMeta[Location,LocationMeta]("location")  {
      val name = field[String]("name")
      val capacity = field[Int]("capacity")
      val coordinate = field[Location]("coordinate")
      val bookings = field[Seq[Booking]]("booking")
    }
    implicit val location: LocationMeta = new LocationMeta()

    class BookingMeta extends ObjectMeta[Booking,BookingMeta]("booking") {
      val time = field("time")
      val event = field[Event]("event")
      val status = field[BookingStatus]("status")
    }

    class EventMeta extends ObjectMeta[Event,EventMeta]("event") {
      // TODO: add fields which needed.

    }


  }



}