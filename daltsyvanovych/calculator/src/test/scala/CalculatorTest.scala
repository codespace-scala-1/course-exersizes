package scala

import org.scalatest.FunSuite

import scala.calculator.Calculator._
import scala.calculator._

class CalculatorTest extends FunSuite {

  test("test plus operation") {
    assert(15 === eval(Plus(Literal(1), Literal(14))))
  }

  test("test minus operation") {
    assert(-13 === eval(Minus(Literal(1), Literal(14))))
  }

  test("test times operation") {
    assert(14 === eval(Times(Literal(2), Literal(7))))
  }

  test("test power operation") {
    assert(16 === eval(Pow(Literal(2), 4)))
  }

  test("test not operation") {
    assert(-2 === eval(Not(Literal(2))))
  }

  test("test sqrt operation") {
    assert(3 === eval(Sqrt(Literal(9))))
  }

  test("test complex expression") {
    assert(3249 === eval(
      Pow(
        Plus(
          Minus(
            Literal(15),
            Not(Literal(32))
          ),
          Times(Literal(2), Literal(5))
        ), 2
      )
    ))
  }
  // +, -, *, ^, !, sqrt

}
