package ParseIP

object ParserIp {

  def greetingReadLine: String =  io.StdIn.readLine("Enter IP, please, to parse it to Array[Byte]. " + Console.BLACK_B +
"// IP  must be as [0-255].[0-255].[0-255].[0-255]\n" + Console.RESET + Console.GREEN)

  def printFromArray(resultIP: Array[Byte]) =
    println(Console.RESET + Console.YELLOW + "{" + resultIP(0) + "," + resultIP(1) +
      "," + resultIP(2) + "," + resultIP(3) + "} " + Console.RESET + " " +
      "<-- result saving in Array[Byte]")
  def printAgainExit = println(Console.BLINK + s"Press ENTER to run ParserIP, or print x exit" + Console.RESET)

}

class ParserIp(inputString: String)  // TODO returned
  {
   // require(checkValidIP(inputString)==true)

    //def apply(inputString: String): ParserIp = ParserIp(inputString)

    def parseIp(inputP: String): Array[Byte] = {
        val splitArrayString = inputP.split('.') //TODO check validIp: working only if . separeted
        val byteArr = new Array[Byte](4)
        for (i <- 0 until byteArr.length) {
          byteArr(i) = (splitArrayString(i).toInt & 0xff).toByte
        }
        byteArr
    }

    def IpFromByte(arr: Array[Byte]): String = {
      s"${arr(0) & 0xFF}.${arr(1) & 0xFF}.${arr(2) & 0xFF}.${arr(3) & 0xFF}"
    }

//    def checkValidIP(ip: String): Boolean = {
//      val splitArray = ip.split('.')
//      val splitInt = List(splitArray(0).toInt, splitArray(1).toInt, splitArray(2).toInt, splitArray(3).toInt)
//      if (ip.contains('.') && (splitArray.length == 4) &&
//        (splitInt(0) >= 0) && (splitInt(0) <= 255) &&
//        (splitInt(1) >= 0) && (splitInt(1) <= 255) &&
//        (splitInt(2) >= 0) && (splitInt(2) <= 255) &&
//        (splitInt(3) >= 0) && (splitInt(3) <= 255)) true
//      else false
//    println("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
//    }



  }

