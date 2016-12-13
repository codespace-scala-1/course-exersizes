package parseint

object ParseInt {

  def parseInt[T](input: T): Int = {
    input match {
      case inp: String => inp.toInt
      case inp: Double => inp.toInt
      case inp: Char => inp.toString.toInt
      case inp => Int.MinValue
    }
  }

  def main(args: Array[String]): Unit = {
    println("hey!")
  }
}