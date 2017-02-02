package shareevent.actorbased

import akka.actor.{ActorSelection, ActorSystem}
import akka.persistence.query.PersistenceQuery
import akka.persistence.query.journal.leveldb.scaladsl.LeveldbReadJournal
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.util.Timeout

import scala.concurrent._
import scala.language.postfixOps
import scala.concurrent.duration._
import scala.util.{Failure, Success}


case object SnaphotEvent

object ActorActivitiesInitializer {

  val externalReadDBEnabled = false

  def init(implicit  actorSystem: ActorSystem): Unit =
  {
    implicit val executionContext = actorSystem.dispatcher
    implicit val materializer = ActorMaterializer()

    actorSystem.scheduler.schedule(initialDelay = 5 minutes,
      interval = 5 minute
     ){
      actorSystem.actorSelection(s"${LocationActor.superviserName}/*")!SnaphotEvent
    }

    if (externalReadDBEnabled) {
      val journal = PersistenceQuery(actorSystem).readJournalFor[LeveldbReadJournal](LeveldbReadJournal.Identifier)

      val source = journal.allPersistenceIds()

      implicit val timeout = Timeout(5 minutes)
      val acceptor = actorSystem.actorSelection("IdSetWriter").resolveOne()
      acceptor.foreach { ref =>
        val sink = Sink.actorRef(ref, ())
        source.runWith(sink)
      }
      acceptor.onComplete{
        case Success(x) => //
        case Failure(ex) => ex.printStackTrace() // TODO: log.
      }
    }





  }


}
