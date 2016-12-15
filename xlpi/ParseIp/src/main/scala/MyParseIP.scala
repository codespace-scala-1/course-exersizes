
object MyParseIp {

    def checkValidIP(strIP: String): Boolean =
    {
        if(strIP.contains('.') && (strIP.split('.').length == 4) &&
          (strIP.split('.')(0).toInt >= 0) && (strIP.split('.')(0).toInt <=255) &&
          (strIP.split('.')(1).toInt >= 0) && (strIP.split('.')(1).toInt <=255) &&
            (strIP.split('.')(2).toInt >= 0) && (strIP.split('.')(2).toInt <=255) &&
              (strIP.split('.')(3).toInt >= 0) && (strIP.split('.')(3).toInt <=255)) true
        else false
    }

    def IPstringToInt(strIP: String ): Either[String,Int] =
    {
            if (checkValidIP(strIP))
            {
                val StringsIP: Array[String] = strIP.split('.')
                val concatStringsIP: String = StringsIP(0) + StringsIP(1) + StringsIP(2) + StringsIP(3)
                Right(concatStringsIP.toInt)
            } else
                Left("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
    }

    def IPstringToLong(strIP: String ): Either[String,Long] =
    {
        if (checkValidIP(strIP))
        {
            val StringsIP: Array[String] = strIP.split('.')
            val concatStringsIP: String = StringsIP(0) + StringsIP(1) + StringsIP(2) + StringsIP(3)
            Right(concatStringsIP.toLong)
        } else
            Left("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
    }

    def parseIp(input: String): Option[Long] =
    {
        IPstringToLong(input).toOption match {
            case Some(value) => Some(value)
            case None => None
        }
    }

    def parseLoop(input_line: String): Unit = {
        if (IPstringToLong(input_line).toOption.isDefined)
        {
            IPstringToLong(input_line).toOption match {
                case Some(value) => if (value > 2147483646) {
                    val res = IPstringToLong(input_line).toOption.get
                    println(s"Parser IP String->Long \n Input: String = $input_line \n" +
                      s" Output: Long = $res")
                } else {
                    val res2 = IPstringToInt(input_line).toOption.get
                    println(s"Parser IP String->Int \n Input: String = $input_line \n" +
                      s" Output: Int = $res2")
                }
                case None => println("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
            }
        }
        else parseLoop(io.StdIn.readLine("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]\n" +
          "Enter only valid IP, please, to parse it to Int or Long\n"))
    }

            def main(args: Array[String]): Unit = {

                val stringIP = io.StdIn.readLine("Enter IP, please, to parse it to Int or Long\n")

                parseLoop(stringIP)

                // use in other functions
                val resultIpLong: Long = parseIp(stringIP).get

            }



}