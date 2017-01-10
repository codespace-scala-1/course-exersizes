package codespace_scala_1.xlpi.ParseIP

/** parse IP: String to Array[Byte]
* requirements: - Input String: "2.112.10.234"
*   OutPut: Array[Byte] = {2,112,10,234}
  *   1.V_TODO how write 234 in type Byte?
  *     0 _127  -  прямой счет 0000 0000, 0000 0001, 0000 00010
  * Using value in app| In bitwise
  *         0           0000 0000
  *         1           0000 0001
  *         2           0000 0010
  *         127         0111 1111
  *         128(?/-0)   1000 0000  (_) - value in Scala
  *         129(-1)     1000 0001
  *         130(-2)     1000 0010
  *         255(-127)   1111 1111
  *   - organise using MyParse class,
  *     that allow convert -22 (234) to 234 in process getting value Array[Int]
  *   2. TDD in developed - TODO write test (by requirements and step by step relize implimentation
  */

case class ParserIp(inputString: String)  // TODO returned
  {
   // require(checkValidIP(inputString)==true)

    def apply(inputString: String): ParserIp = ParserIp(inputString)

    def parseIp(inputP: String): Option[Array[Byte]] = {
      try {
        val splitArrayString = inputP.split('.') // work only if . separeted
        val byteArr = new Array[Byte](4)
        for (i <- 0 until byteArr.length) {
          byteArr(i) = (splitArrayString(i).toInt & 0xff).toByte
        }
        Some(byteArr)
      }
      catch {
        case e: Exception => None// TODO exactly error handling or tranzit up
      }
    }

    def printIpFromByte (arr: Array[Byte]) = {
      println(s"${arr(0) & 0xFF}.${arr(1) & 0xFF}.${arr(2) & 0xFF}.${arr(3) & 0xFF}")
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
//    }



  }
