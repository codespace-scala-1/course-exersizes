package parseint

import org.scalatest._

class TestParseInt extends FlatSpec with Matchers {
  "Parse String" should "say hello" in {
    ParseInt.parseInt("3") shouldEqual 3
  }
  
  "Parse Dobule" should "say hello" in {
    ParseInt.parseInt(3.0) shouldEqual 3
  }
  
  "Parse Char" should "say hello" in {
    ParseInt.parseInt('3') shouldEqual 3
  }
  
  "Parse Boolean" should "say hello" in {
    ParseInt.parseInt(true) shouldEqual Int.MinValue
  }
  
  "Parse empty String" should "say hello" in {
    ParseInt.parseInt("") shouldEqual Int.MinValue
  }
  
  "Parse invalid String" should "say hello" in {
    ParseInt.parseInt("abc") shouldEqual Int.MinValue
  }
}
