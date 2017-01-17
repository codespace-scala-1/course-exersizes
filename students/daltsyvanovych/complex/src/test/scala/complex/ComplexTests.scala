package complex

import org.scalatest.FunSuite

import complex.Complex._


class ComplexTests extends FunSuite {

  test("test string method") {
    "(3 + 4i)" === Complex(3, 4).toString()
  }

  test("test string method with negative im") {
    "(3 - 2i)" === Complex(3, -2).toString()
  }

  test("add complex numbers") {
    Complex(3, -3) === Complex(2, 3) + Complex(1, -6)
  }

  test("minus complexes") {
    Complex(9, -1) === Complex(5, -2) - Complex(-4, -1)
  }

  test("times complexes") {
    Complex(10, 5) === Complex(2, -1) * Complex(3, 4)
  }

  test("transformations") {
    Complex(10, -5) === Complex(2, -1) * 5
  }

  test("check i value") {
    Complex(0, 1) === i
  }

  test("check int transform") {
    Complex(4, 1) === 4 + i
  }

  test("check double transform") {
    Complex(4D, 1) === 4D + i
  }

  test("check float transform") {
    Complex(4.12, 1) === 4.12 + i
  }

  test("expression building") {
    Complex(2, 3) === 2 + 3 * i
  }

  test("high expression building") {
    Complex(2, 3) === (2 + 3 * i) * (1 - 1 * i)
  }

  test("dividing test") {
    Complex(4, -2) === (4 + 2 * i) / (1 - 1 * i)
  }

  test("dividing test 2") {
    Complex(2, -1) === (4 + 2 * i) / (2 - 2 * i)
  }

  test("can't be 0") {
    intercept[IllegalArgumentException] {
      2 / (0 - 0 * i)
    }
  }

}
