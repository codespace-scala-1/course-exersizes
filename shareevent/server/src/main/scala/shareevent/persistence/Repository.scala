package shareevent.persistence

import org.joda.time.Interval
import shareevent.model.Role.Role
import shareevent.model._

import scala.util.Try

trait Repository
{
  import Repository._


  trait DAO[K, T] {

    def query(q: QueryDSL.TableSelectionExpression[T]): Try[Seq[T]] = ???

    def store(obj: T): Try[T]

    def retrieve(key: K): Try[Option[T]]

    def delete(key: K): Try[Boolean]

    def merge(instance: T): Try[T]
  }


  //def retrieveDao[K, T](): DAO[K,T]

  val personDAO: DAO[String,Person]


  val locationDAO:  DAO[Long,Location]




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

    implicit val person: PersonMeta = new PersonMeta

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