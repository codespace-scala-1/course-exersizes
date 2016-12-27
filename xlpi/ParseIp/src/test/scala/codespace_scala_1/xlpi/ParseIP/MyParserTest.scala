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

   val parsed: Array[Byte] = parseIp("12.234.121.5")
    assert(parsed === {(12 & 0xff),(234 & 0xFF),(121 & 0xFF),(5 & 0xFF)})
  }
}
