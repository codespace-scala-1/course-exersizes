package shareevent

import org.scalatest.BeforeAndAfterAll
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.testkit.{DefaultTimeout, ImplicitSender, TestActors, TestKit, TestProbe}
import actorbased._
import org.joda.time.DateTime
import shareevent.model.{Coordinate, Event, Location, Person, ScheduleItem}

import scala.concurrent.duration._
import scala.collection.immutable

class LocationActorFlowTest extends TestKit(ActorSystem(
  "TestKitUsageSpec"))
  with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll {
    shutdown()
  }

  val testLocationId = Location.id(1L)
  val testLocation = Location(id = Some(testLocationId),
    name = "DataHub",
    capacity = 30,
    coordinate = Coordinate(15,20))
  val someTime = DateTime.now()
  val scalaUaEventId = Event.id(1L)
  val successfulPrebookReply = PreBookReply(true)
  val failedPrebookReply = PreBookReply(false)

  val testLocationRef = system.actorOf(LocationActor.propsFromLocation(testLocation))
  // prebook event -> cancel, check state (bookings do not contain this one)
  // prebook event, prebook another one for the same time, check reply is false
  //    prebook another one with more people than capacity, check reply is false
  // prebook event twice for different dates, cancel one booking, check another one is still there
  // prebook event twice for different dates, cancel event, check no bookings with current event id are ther
  // prebook event twice, confirm the second instance, check that correct booking in state has a Final status
  //    cancel confirmed booking, check it has cancelled
  // prebook event twice, confirm the second instance, cancel event, check the bookings are gone

  val scheduleItem1 = ScheduleItem(eventId = scalaUaEventId,
                                   locationId = testLocationId,
                                   time = someTime,
                                   participants = Seq[Person.Id]())
  val scheduleItem2 = scheduleItem1.copy(eventId = Event.id(2L))

  "locationActor" should {
    "cancel pre-booked event" in {
      testLocationRef ! PreBook(scheduleItem1)
      expectMsg(successfulPrebookReply)
      testLocationRef ! GetState
      val newStatePrebooked = expectMsgType[Location]
      newStatePrebooked.bookings should have size (1)

      testLocationRef ! Cancel(scheduleItem1)
      testLocationRef ! GetState
      val newStateCancelled = expectMsgType[Location]
      println(newStateCancelled.bookings)
      newStateCancelled.bookings shouldBe 'empty
    }

    "fail to prebook two events for the same time" in {
      testLocationRef ! PreBook(scheduleItem1)
      expectMsgType[Any]
      testLocationRef ! PreBook(scheduleItem2)
      expectMsg(failedPrebookReply)
    }
  }


}
