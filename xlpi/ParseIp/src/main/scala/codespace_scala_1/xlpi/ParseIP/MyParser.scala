package codespace_scala_1.xlpi.MyParser

import codespace_scala_1.xlpi.ParseIP.parseIp

object MyParser {

  def main(args: Array[String]): Unit = {

    val stringIP = io.StdIn.readLine("Enter IP, please, to parse it to Int or Long\n")

    parseIp(stringIP) match {
      case Some(s) => println("result Array[Byte] = (" + s(0) +"," + s(1) +
        ","  + s(2)+","  + s(3)+ ")")
      case None => println("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
    }


  }



}
