package shareevent


import scala.language.higherKinds
import org.scalatest.FunSpec
import shareevent.model.{Person, ScheduleItem}

import scala.util.Try
import cats._
import cats.syntax.all._


class OverallFlowTest extends FunSpec {



  def bestScheduleLocations[M[_]](scheduleItems: Seq[ScheduleItem])(implicit m:Monad[M]): DomainContext[M] => M[ScheduleItem] =
  {
    ???
  }

  def runEvent[M[_]](service:DomainService[M])(organizer: Person, title: String)(implicit m:Monad[M]): DomainContext[M] => M[ScheduleItem] =
    ctx => {
      for {event <- service.createEvent(organizer, title, theme = "*")(ctx)
           scheduledItems <- service.possibleLocationsForEvent(event)(ctx)
           scheduledItem <- bestScheduleLocations[M](scheduledItems)(m)(ctx)
      } yield scheduledItem
    }


}
