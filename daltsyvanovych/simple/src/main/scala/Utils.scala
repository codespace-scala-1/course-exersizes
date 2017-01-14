import scala.util.Try
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Utils {

def parseInt(s: String) = Try(s.toInt).toOption

  def parseIp(ip: String) = {
    Try(ip.split('.').ensuring(_.length == 4)
      .map(_.toInt).ensuring(_.forall(x => x >= 0 && x < 256))
      .zip(Array(256L * 256L * 256L, 256L * 256L, 256L, 1L))
      .map { case (x, y) => x * y }
      .sum).toOption
  }
 
  def parseEthernetAddress(s: String) = ???

}
