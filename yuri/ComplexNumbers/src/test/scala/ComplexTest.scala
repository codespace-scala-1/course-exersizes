import org.scalatest._
import yuri.math._

class ComplexTest extends FunSuite
{
  test("add gives right result") {
    val a = Complex(1, 2)
    val b = Complex(2, 3)

    assert(a+b === Complex(3, 5))
  }

  test("subtract gives right result") {
    val a = Complex(2, 3)
    val b = Complex(4, 8)

    assert(a-b === Complex(-2, -5))
  }

  test("unary minus gives right result") {
    val a = Complex(2, 3)
    assert(-a === Complex(-2, -3))
  }

  test("unary plus gives right result") {
    val b = Complex(4, 8)
    assert(+b === Complex(4, 8))
  }

  test("conj gives right result") {
    val a = Complex(2, 3)
    assert(~a === Complex(2, -3))
  }

  test("lengthSq gives right result") {
    val b = Complex(3, 4)
    assert(+b.lengthSq() === 25)
  }

  test("product gives right result") {
    val a = Complex(1, 2)
    val b = Complex(2, 3)
    assert(a*b === Complex(-4, 7))
  }

  test("division gives right result") {
    val a = Complex(1, 2)
    val b = Complex(3, 4)
    assert(a/b === Some(Complex(0.44, 0.08)))
  }

  test("division by ComplexZero gives None") {
    val a:Complex = Complex(1, 2)
    assert(a / ComplexZero === None)
  }

  test("division by Double gives right result") {
    val a = Complex(1, 2)
    assert(a / 2.0 === Some(Complex(0.5, 1)))
  }

  test("division by zero Double gives None") {
    val a = Complex(1, 2)
    assert(a / 0.0 === None)
  }

  test("reciprocal gives right result") {
    val a = Complex(3, 4)
    assert(a.reciprocal === Some(Complex(0.12, -0.16)))
  }

  test("implicit from Double gives right result") {
    val a:Complex = 2.0
    assert(a === Complex(2, 0))
  }

  test("implicit from Int gives right result") {
    val a:Complex = 2
    assert(a === Complex(2, 0))
  }

  test("ComplexZero equals Complex(0, 0)") {
    assert(ComplexZero === Complex(0, 0))
  }

  test("equality testing") {
    val a:ComplexNumber = Complex(2 + 1e-15, 3 + 1e-15)
    assert(a === Complex(2, 3))
  }

  test("Zero reciprocal") {
    assert(ComplexZero.reciprocal() === None, "explicit ComplexZero")
    assert(Complex(0, 0).reciprocal() === None, "implicit ComplexZero")
  }

  test("Non-zero reciprocal") {
    val a:ComplexNumber = Complex(1, 2)
    assert(a.reciprocal() === Some(Complex(0.2, -0.4)))
  }

  test("toString works fine") {
    assert(Complex(1, 2).toString === "1.0 + 2.0*i", "positive im")
    assert(Complex(1, -2).toString === "1.0 - 2.0*i", "negative im")
    assert(Complex(0, 2).toString === "2.0*i", "pure im")
    assert(Complex(-5, 0).toString === "-5.0", "pure re")
  }
}