package shareevent.persistence

import cats.{Monad, ~>}
import cats.syntax.all._
import org.joda.time.{DateTime, Duration=>JodaDuration, Interval}
import shapeless.LabelledGeneric
import shareevent.model.Role.Role
import shareevent.model._
import shareevent.persistence.QueryDSL.ObjectMeta

import scala.util.{Failure, Success, Try}
import scala.language.higherKinds

trait Repository[M[_]]
{
  import Repository._

  implicit val mMonad: Monad[M]
  implicit val tryToMonad: ~> [Try, M]


  trait DAO[K, T] {

    def query[MT<:ObjectMeta[T,MT]](q: QueryDSL.QueryExpression[MT]): M[Seq[T]]

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


  val locationDAO:  DAO[Location.Id,Location]


  val eventsDAO: DAO[Event.Id,Event]


}

object Repository
{

  // TODO: implement via macroses and/or shapeless.
  object Objects {

    import QueryDSL._


    import shapeless.record._
    class PersonMeta extends ObjectMeta[Person,PersonMeta]("person") {
      def toMap(x:Person):Map[Symbol,Any] = LabelledGeneric[Person].to(x).toMap

      val login = field[String]("login")
      val password = field[String]("password")
      val role = field[Role]("role")
      val email = field[Option[String]]("email")
      val slackId = field[Option[String]]("slackId")
      val phoneNo = field[Option[String]]("phoneNo")
    }

    implicit val personMeta: PersonMeta = new PersonMeta()

    /*case class Booking(time: Interval,
                       event: Event,
                       status: BookingStatus = BookingStatus.Preliminary)
                       */


    class LocationMeta extends ObjectMeta[Location,LocationMeta]("location")  {
      def  toMap(x:Location):Map[Symbol,Any] = LabelledGeneric[Location].to(x).toMap
      val name = field[String]("name")
      val capacity = field[Int]("capacity")
      val coordinate = field[Location]("coordinate")
      val bookings = field[Seq[Booking]]("booking")
    }
    implicit val locationMeta: LocationMeta = new LocationMeta()

    class BookingMeta extends ObjectMeta[Booking,BookingMeta]("booking") {
      def  toMap(x:Booking):Map[Symbol,Any] = LabelledGeneric[Booking].to(x).toMap
      val time = field[DateTime]("time")
      val event = field[Event]("event")
      val status = field[BookingStatus]("status")
    }

    class EventMeta extends ObjectMeta[Event,EventMeta]("event") {
      // TODO: add fields which needed.
      def  toMap(x:Event):Map[Symbol,Any] = LabelledGeneric[Event].to(x).toMap

      val id = field[Option[Event.Id]]("id")
      val title = field[String]("title")
      val theme = field[String]("theme")
      val organizerId = field[Person.Id]("organizerId")
      val organizerCost = field[Money]("organizerCost")
      val status = field[EventStatus]("eventStatus")
      val created = field[DateTime]("created")
      val duration = field[JodaDuration]("duration")
      val scheduleWindow = field[JodaDuration]("scheduleWindow")
      val minParticipantsQuantity = field[Int]("minParticipantsQuantity")

    }
    implicit val eventMeta: EventMeta = new EventMeta


  }



}