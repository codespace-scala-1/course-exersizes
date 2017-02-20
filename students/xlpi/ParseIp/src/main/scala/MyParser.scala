package ParseIP

import java.io.{BufferedReader, InputStreamReader}
import ParserIp._

object MyParser {

  val in = new BufferedReader(new InputStreamReader(System.in))

  def main(args: Array[String]) {

    do {
      val stringIP = greetingReadLine
      val parser = new ParserIp(stringIP)
      val resultIP: Array[Byte] = parser.parseIp(stringIP)
      printFromArray(resultIP)
      println(s"${Console.GREEN} ${parser.IpFromByte(resultIP)} ${Console.RESET} <-- parsed IP\n")
      printAgainExit
    } while (!(in.readLine() == "x"))
  }
}
