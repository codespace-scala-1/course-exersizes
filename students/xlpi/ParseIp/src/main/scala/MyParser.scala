package ParseIP

import java.io.{BufferedReader, InputStreamReader}

object MyParser {

  val in = new BufferedReader(new InputStreamReader(System.in))

  def main(args: Array[String]) {

    do {
      val stringIP = io.StdIn.readLine("Enter IP, please, to parse it to Array[Byte]. " + Console.BLACK_B +
        "// IP  must be as [0-255].[0-255].[0-255].[0-255]\n" + Console.RESET + Console.GREEN)
      print(Console.RESET)
      val parser = new ParserIp(stringIP)
      val resultIP: Array[Byte] = parser.parseIp(stringIP)
      println(Console.YELLOW + "{" + resultIP(0) + "," + resultIP(1) +
        "," + resultIP(2) + "," + resultIP(3) + "} " + Console.RESET + " " +
        "<-- result saving in Array[Byte]")
      println(s"${Console.GREEN} ${parser.IpFromByte(resultIP)} ${Console.RESET} <-- parsed IP\n")
      println(Console.BLINK + s"Press ENTER to begin, or print x exit" + Console.RESET)
    } while (!(in.readLine() == "x"))
  }
}
