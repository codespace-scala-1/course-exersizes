package shareevent


<<<<<<< HEAD


import org.scalatest._
import shareevent.model.{Person, ScheduleItem}
=======
import shareevent.DomainContext
//import model._

import org.scalatest._
>>>>>>> 6fbc762557bf7eca082b517ed4a6fa29aa4a9195

import scala.util.Try

class OverallFlowTest extends FunSpec {


<<<<<<< HEAD

  def bestScheduleLocations(scheduleItems: Seq[ScheduleItem]): DomainContext => Try[ScheduleItem] = ???

  def runEvent(service:DomainService)(organizer: Person, title: String): DomainContext => Try[ScheduleItem] =
    ctx => {
      for {event <- service.createEvent(organizer, title, theme = "*")(ctx)
           scheduledItems = service.possibleLocationsForEvent(event)(ctx)
           scheduledItem <- bestScheduleLocations(scheduledItems)(ctx)
=======
  /*
  def bestScheduleLocations(scheduleItems: Seq[ScheduleItem]): DomainContext => Try[ScheduleItem] = ???
  def runEvent(organizer: Organizer, title: String): DomainContext => Try[ScheduleItem] =
    repo => {
      for {event <- createEvent(organizer, title, theme = "*")(repo)
           scheduledItems = possibleLocationsForEvent(event)(repo)
           scheduledItem <- bestScheduleLocations(scheduledItems)(repo)
>>>>>>> 6fbc762557bf7eca082b517ed4a6fa29aa4a9195
      } yield {
        scheduledItem
      }
    }
<<<<<<< HEAD



}
=======
    */


}
>>>>>>> 6fbc762557bf7eca082b517ed4a6fa29aa4a9195
