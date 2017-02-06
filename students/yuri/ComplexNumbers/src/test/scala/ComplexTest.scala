import org.scalatest._
import yuri.math._

class ComplexTest extends FunSuite
{
  test("add gives right result") {
    val a = ComplexNumber[Double](1, 2)
    val b = ComplexNumber[Double](2, 3)

    assert(a+b === ComplexNumber[Double](3, 5))
  }

  test("subtract gives right result") {
    val a = ComplexNumber[Double](2, 3)
    val b = ComplexNumber[Double](4, 8)

    assert(a-b === ComplexNumber[Double](-2, -5))
  }

  test("unary minus gives right result") {
    val a = ComplexNumber[Double](2, 3)
    assert(-a === ComplexNumber[Double](-2, -3))
  }

  test("unary plus gives right result") {
    val b = ComplexNumber[Double](4, 8)
    assert(+b === ComplexNumber[Double](4, 8))
  }

  test("conj gives right result") {
    val a = ComplexNumber[Double](2, 3)
    assert(~a === ComplexNumber[Double](2, -3))
  }

  test("lengthSq gives right result") {
    val b = ComplexNumber[Double](3, 4)
    assert(+b.lengthSq === 25)
  }

  test("product gives right result") {
    val a = ComplexNumber[Double](1, 2)
    val b = ComplexNumber[Double](2, 3)
    assert(a*b === ComplexNumber[Double](-4, 7))
  }

  test("division gives right result") {
    val a = ComplexNumber[Double](1, 2)
    val b = ComplexNumber[Double](3, 4)
    assert(a/b === ComplexNumber[Double](0.44, 0.08))
  }

  test("division by complex zero") {
    val a = ComplexNumber[Double](1, 2)
    assertThrows[IllegalArgumentException] { a / ComplexNumber[Double](0, 0) }
  }

  test("division by Double gives right result") {
    val a = ComplexNumber[Double](1, 2)
    assert(a / 2.0 === ComplexNumber(0.5, 1))
  }

  test("division by zero Double") {
    val a = ComplexNumber[Double](1, 2)
    assertThrows[IllegalArgumentException] { a / 0.0 }
  }

  test("reciprocal gives right result") {
    val a = ComplexNumber[Double](3, 4)
    assert(a.reciprocal === ComplexNumber(0.12, -0.16))
  }

  test("implicit from Double gives right result") {
    val a:ComplexNumber[Double] = 2.0
    assert(a === ComplexNumber[Double](2, 0))
  }

  test("implicit from Int gives right result") {
    val a:ComplexNumber[Double] = 2
    assert(a === ComplexNumber[Double](2, 0))
  }

  test("equality testing") {
    val a = ComplexNumber[Double](2 + 1e-15, 3 + 1e-15)
    assert(a === ComplexNumber[Double](2, 3))
  }

  test("Zero reciprocal") {
    assertThrows[IllegalArgumentException] { ComplexNumber[Double](0, 0).reciprocal }
  }

  test("Non-zero reciprocal") {
    val a = ComplexNumber[Double](1, 2)
    assert(a.reciprocal() === ComplexNumber[Double](0.2, -0.4))
  }

  test("toString works fine") {
    assert(ComplexNumber[Double](1, 2).toString === "1.0 + 2.0*i", "positive im")
    assert(ComplexNumber[Double](1, -2).toString === "1.0 - 2.0*i", "negative im")
    assert(ComplexNumber[Double](0, 2).toString === "2.0*i", "pure im")
    assert(ComplexNumber[Double](-5, 0).toString === "-5.0", "pure re")
  }

  test("testing operations with im literal") {
    assert((3 -2*im)/(1-1*im) === ComplexNumber[Double](2.5, 0.5))
  }
}