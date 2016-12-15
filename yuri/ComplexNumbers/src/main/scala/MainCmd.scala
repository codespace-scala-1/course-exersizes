import yuri.math._

object MainCmd {
  def main(args: Array[String]): Unit = {

    val a = Complex(1, 2)
    val b = Complex(3, 4)

    val two: Complex = 2.0

    val prod = a * b / two

    println(a * b)
    println((a * b) / 2)
    println((a * b) / two)
    println(two)
    println(a / b)

    val aa: Complex = Complex(1, 2)
    println(aa / ComplexZero)

  }
}
