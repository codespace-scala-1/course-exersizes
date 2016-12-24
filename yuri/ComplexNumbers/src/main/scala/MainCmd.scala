import yuri.math._

object MainCmd {
  def main(args: Array[String]): Unit = {

    val a: ComplexNumber[Double] = ComplexNumber(1, 2)
    val b: ComplexNumber[Double] = ComplexNumber(3, 4)

    val two: ComplexNumber[Double] = 2.0

    val prod = a * b / two

    println(a * b)
    println((a * b) / 2)
    println((a * b) / two)
    println(two)
    println(a / b)

    val aa = ComplexNumber(1, 2)
    println(aa / ComplexNumber(0, 0))

  }
}
