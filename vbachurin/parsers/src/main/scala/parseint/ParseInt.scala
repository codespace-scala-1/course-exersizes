package parseint

import scala.util.Try
import scala.util.Success
import scala.util.Failure

object ParseInt {

  def parseInt[T](input: T): Int =
    Try(convertInt(input)) match {
      case Success(v) =>
        convertInt(input)
      case Failure(e) =>
        Int.MinValue
    }

  def convertInt[T](input: T): Int = {
    Option(input) match {
      case Some(string: String) => string.toInt
      case Some(double: Double) => double.toInt
      case Some(char: Char) => char.toString.toInt
      case _ => Int.MinValue
    }
  }

  def main(args: Array[String]): Unit = {
    println("hey!")
  }
}