package shareevent

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.json4s.NoTypeHints
import org.json4s.native.Serialization

import scala.concurrent.ExecutionContext

trait GenericClientService {
  implicit val defaultFormats = Serialization.formats(NoTypeHints)
  implicit val serialization = Serialization

  implicit val execCtx: ExecutionContext
  implicit val actorSystem: ActorSystem
  implicit val materializer: Materializer
}
