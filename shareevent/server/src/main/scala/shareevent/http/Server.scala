package shareevent.http

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

class Server extends App{
  val actorSystem = ActorSystem
  val materialize = ActorMaterializer

}
