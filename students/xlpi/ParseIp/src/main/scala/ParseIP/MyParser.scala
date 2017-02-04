package ParseIP

import ParserIp

object MyParser {

  def main(args: Array[String]): Unit = {

    /** parse IP: String to Array[Byte]
      * requirements: - Input String: "2.112.10.234"
      * OutPut: Array[Byte] = {2,112,10,-22}
      */

    val stringIP = io.StdIn.readLine("Enter IP, please, to parse it to Int or Long\n")
           val parser = new ParserIp(stringIP)  
           parser.parseIp(stringIP) match {
              case Some(s) => println("result Array[Byte] = (" + s(0) +"," + s(1) +
                ","  + s(2)+","  + s(3)+ ")")
              case None => println("Wrong IP template!, must be [0-255].[0-255].[0-255].[0-255]")
            }

    // parse IP from String and print result
    parser.printIpFromByte(parser.parseIp(stringIP).get)
    
    
