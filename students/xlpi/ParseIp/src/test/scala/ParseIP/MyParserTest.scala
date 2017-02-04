package ParseIP

import org.scalatest._
import org.scalatest.FunSpec

class MyParserTest extends FunSuite with BeforeAndAfterAll {

  override def beforeAll() = {
    Console.println("Hi in test Parsers")
  }

  test("get type Byte in output") {
        val ip0 = "12"
    val testDone = true
        val ip1 = "234"
        val ip2 = "121"
        val ip3 = "5"
      val myIp = s"$ip0.$ip1.$ip2.$ip3"
    val parser = new ParserIp(s"$ip0.$ip1.$ip2.$ip3")
    val resultIP: Array[Byte] = parser.parseIp(myIp).get

    assert(parser.printIpFromByte(resultIP) === myIp)
  }
}
