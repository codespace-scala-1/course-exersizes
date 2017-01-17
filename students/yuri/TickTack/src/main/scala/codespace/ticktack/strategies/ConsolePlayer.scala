package codespace.ticktack.strategies

import codespace.ticktack.{Field, Label, Player, Rules}
import scala.io.StdIn.{readLine}

class ConsolePlayer(label:Label, rules:Rules) extends BasePlayer(label, rules) {

  override def nextStep(f: Field): Either[String, ((Int, Int), Player)] = {

    var ij: Either[String, (Int,Int)] = Left("")

    do {
      print("Enter your code: ")
      val line = readLine()
      ij = stringToPos(line)

      if(ij.isLeft)
        println(ij.left.get + "\r\n")
    } while(ij.isLeft)

    Right((ij.right.get, this))
  }

  override def tell(s: String): Player = {
    println(s)
    this
  }

  def stringToPos(code:String):Either[String, (Int,Int)] =
  {
    if (code.length != 2) Left("Code length should be equal to 2 chars")
    else try {
      val rexpr="([A-C])([0-2])".r
      val l = rexpr.findAllIn(code)

      if (l.hasNext) {
        val x = l.group(1).charAt(0) - 'A'
        val y = l.group(2).charAt(0) - '0'
        val xy = (x.toInt, y.toInt)

        if (x < 3 && x >= 0 && y >= 0 && y < 3) {
          Right(xy)
        } else Left("Indices are out of 0..2 bound")
      } else Left("Couldn't parse the code, pattern is [A-C][0-2]")
    } catch {
      case ex: Exception =>
        //ex.printStackTrace()
        Left(ex.getMessage)
    }

  }

}
