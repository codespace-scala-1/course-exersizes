import scala.collection.mutable.ArrayBuffer

// parse IP: String to Array[Byte]
object MyParser {
/*
  case class parseIp(inputString: String)   // TODO returned
  {
    // TODO require(inputString.toCharArray.foreach(i ))

//    if (code.length != 2) None
//    else try {
//      val rexpr="([A-C])([0-3])".r
//      val l = rexpr.findAllIn(code)
//      if (l.hasNext) {
//        val x = l.group(0).charAt(0) - 'A'
//        val y = l.group(1).charAt(0) - '0'
//        val xy = (x.toInt, y.toInt)
//        if (x < 3 && x >= 0 && y >= 0 && y < 3) {
//          Some(xy)
//        } else None
//      } else None
//    } catch {
//      case ex: Exception =>



    def checkValidIP(ip: String): Boolean = {
      val splitArray = ip.split('.')
      val splitInt = List(splitArray(0).toInt, splitArray(1).toInt, splitArray(2).toInt, splitArray(3).toInt)
      if (ip.contains('.') && (splitArray.length == 4) &&
        (splitInt(0) >= 0) && (splitInt(0) <= 255) &&
        (splitInt(1) >= 0) && (splitInt(1) <= 255) &&
        (splitInt(2) >= 0) && (splitInt(2) <= 255) &&
        (splitInt(3) >= 0) && (splitInt(3) <= 255)) true
      else false
    }
*/



    def parseIp(inputP: String): Option[Array[Byte]] = {
      try {
        val splitArrayString = inputP.split('.')
        val byteArr = new Array[Byte](4)
        for (i <- 0 until byteArr.length) {
          byteArr(i) = splitArrayString(i).toInt.toByte //TODO correct
        }
        val byteArrOut0: Byte = (byteArr(0) & 0xFF).toByte
        val byteArrOut1: Byte = (byteArr(1) & 0xFF).toByte
        val byteArrOut2: Byte = (byteArr(2) & 0xFF).toByte
        val byteArrOut3: Byte = (byteArr(3) & 0xFF).toByte
        val byteOut = new Array[Byte](4)
        byteOut(0) = byteArrOut0
        byteOut(1) = byteArrOut1
        byteOut(2) = byteArrOut2
        byteOut(3) = byteArrOut3
        Some(byteOut)
      }
      catch {
        case e: Exception => None
      }
    }


  def main(args: Array[String]): Unit = {

    val stringIP = io.StdIn.readLine("Enter IP, please, to parse it to Int or Long\n")

    MyParser.parseIp(stringIP) match {
      case Some(s) => println("result Array[Byte] = (" + s(0) +"," + s(1) +
        ","  + s(2)+","  + s(3)+ ")")
      case None => println("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
    }


    // use in other functions
   // val ipToOption: Option[Long] = new parseIp(stringIP).parseIp(stringIP) // TODO change .get()? because None.get throw exception

  }



}