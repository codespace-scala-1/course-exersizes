import org.scalatest.FunSuite

import scala.calculator.Literal

class ParserTest extends FunSuite {

  test("test pars") {
    Literal(1) === "1"
  }

}
