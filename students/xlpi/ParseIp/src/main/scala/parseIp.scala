package ParseIP

case class ParserIp(inputString: String)  // TODO returned
  {
    def apply(inputString: String): ParserIp = ParserIp(inputString)

    def parseIp(inputP: String): Option[Array[Byte]] = {
      try {
        val splitArrayString = inputP.split('.') // check working only if . separeted
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

    def printIpFromByte (arr: Array[Byte]): String = {
      s"${arr(0) & 0xFF}.${arr(1) & 0xFF}.${arr(2) & 0xFF}.${arr(3) & 0xFF}"
    }

  }
