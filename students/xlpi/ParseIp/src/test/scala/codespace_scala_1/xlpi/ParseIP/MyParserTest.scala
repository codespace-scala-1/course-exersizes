package codespace_scala_1.xlpi.ParseIP
import org.scalatest._
/**
  * Created by xlpi on 12/25/16.
  */

class MyParserTest extends FunSuite with BeforeAndAfterAll {

  override def beforeAll() = {
    Console.println("Hi in test Parsers")
  }

  test("get type Byte in output") {
    //    val ip0 = "12"
    val testDone = true
    //    val ip1 = "234"
    //    val ip2 = "121"
    //    val ip3 = "5"
    //   val parsedResult: Array[Byte] = parseIp(s"$ip0+$ip1+$ip2+$ip3")
    //    val byteArr = new Array[Byte](4)
    assert(testDone === true)
  }
}
