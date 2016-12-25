package codespace_scala_1.xlpi.ParseIP

/** parse IP: String to Array[Byte]
* requirements: - Input String: "2.112.10.234"
*   OutPut: Array[Byte] = {2,112,10,234}
  *   1. TODO how write 234 in type Byte? - organise using MyParse class,
  *     that allow convert -22 (234) to 234 in process getting value Array[Int]
  *   2. TDD in developed - TODO write test (by requirements and step by step relize implimentation
  */

case class parseIp(inputString: String)  // TODO returned
  {
   // require(checkValidIP(inputString)==true)

    def apply(inputString: String): parseIp = new parseIp(inputString)

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
        case e: Exception => None// TODO exactly error handling or tranzit up
      }
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
