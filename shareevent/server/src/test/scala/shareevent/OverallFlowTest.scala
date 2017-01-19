package shareevent




import org.scalatest._
import shareevent.model.{Person, ScheduleItem}

import scala.util.Try

class OverallFlowTest extends FunSpec {



  def bestScheduleLocations(scheduleItems: Seq[ScheduleItem]): DomainContext => Try[ScheduleItem] = ???

  def runEvent(service:DomainService)(organizer: Person, title: String): DomainContext => Try[ScheduleItem] =
    ctx => {
      for {event <- service.createEvent(organizer, title, theme = "*")(ctx)
           scheduledItems = service.possibleLocationsForEvent(event)(ctx)
           scheduledItem <- bestScheduleLocations(scheduledItems)(ctx)
      } yield {
        scheduledItem
      }
    }



}
