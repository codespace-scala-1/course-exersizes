package parseip

import scala.util.Try
import scala.util.Success
import scala.util.Failure

object ParseIp {

  def parseIp[T](input: String): Boolean =
    Try(checkIp(input)) match {
      case Success(v) =>
        v
      case Failure(e) =>
        false
    }

  def checkIp(ip: String): Boolean = {
    ip.split("\\.").forall(x => 0 <= x.toInt && x.toInt < 255)
  }
}