package org.codespace.complexnumbers

import org.scalatest.FunSuite

class ComplexNumberTest extends FunSuite {

  test("should add two complex numbers") {
    val complexNumber1 = new ComplexNumber(4,7)
    val complexNumber2 = new ComplexNumber(5,-12)

    val complexSum = complexNumber1.add(complexNumber2)

    assert(complexSum == new ComplexNumber(9, -5))
  }

  test("should properly print complex number") {

    val complexNumber1 = new ComplexNumber(2,5)
    assert(complexNumber1.toString == "2.0 + i*5.0")

    val complexNumber2 = new ComplexNumber(2, -5)
    assert(complexNumber2.toString == "2.0 - i*5.0")

    val complexNumber3 = new ComplexNumber(0, -5)
    assert(complexNumber3.toString == "- i*5.0")

    val complexNumber4 = new ComplexNumber(0, 5)
    assert(complexNumber4.toString == "i*5.0")

    val complexNumber5 = new ComplexNumber(-5, 0)
    assert(complexNumber5.toString == "-5.0")

    val complexNumber6 = new ComplexNumber(0, 0)
    assert(complexNumber6.toString == "0")
  }

  test("should negate complex number") {
    val complexNumber1 = new ComplexNumber(2,5)
    assert(-complexNumber1 == new ComplexNumber(-2, -5))
  }
}
