package parseip

import org.scalatest._

class TestParseIp extends FlatSpec with Matchers {
  "The ParseIp object" should "parse localhost IP" in {
    ParseIp.parseIp("127.0.0.1") shouldEqual true
  }
  
  "The ParseIp object" should "detect invalid IP" in {
    ParseIp.parseIp("327.0.0.1") shouldEqual false
  }
  
  "The ParseIp object" should "detect not IP" in {
    ParseIp.parseIp("aa.0.0.1") shouldEqual false
  }
}
