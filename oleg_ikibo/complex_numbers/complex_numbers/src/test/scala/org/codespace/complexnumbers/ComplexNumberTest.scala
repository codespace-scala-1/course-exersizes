package org.codespace.complexnumbers

import org.scalatest.FunSuite

class ComplexNumberTest extends FunSuite {

  test("should add two complex numbers") {
    val complexNumber1 = new ComplexNumber(4,7)
    val complexNumber2 = new ComplexNumber(5,-12)

    val complexSum = complexNumber1.add(complexNumber2)

    assert
  }

  test("complex string representation") {

    val complexNumber1 = new ComplexNumber(2,5)

    assert(complexNumber1.toString == "2 + i*5")

    val complexNumber2 = new ComplexNumber(2, -5)

    assert(complexNumber2.toString == "2 - i*5")
  }

}
