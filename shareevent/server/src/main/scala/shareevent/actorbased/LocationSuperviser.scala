package shareevent.actorbased

import akka.actor.Props
import akka.pattern.{Backoff, BackoffSupervisor}

import scala.concurrent.duration._

object LocationSupervisor {

  def props(props: Props, name:String) = BackoffSupervisor.props(
      Backoff.onStop(
        props,
        childName = name,
        minBackoff = 3.seconds,
        maxBackoff = 30.seconds,
        randomFactor = 0.2 // adds 20% "noise" to vary the intervals slightly
      ))



}
