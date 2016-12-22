package codespace.ticktack.strategies

import codespace.ticktack.{Label, Rules}

abstract class ConsolePlayer(label:Label,rules:Rules) extends BasePlayer(label,rules) {

  Console.println(s"Hi, my label is ${label}, this.label = ${this.label}")

  def stringToPos(code:String):Option[(Int,Int)] =
  {
    if (code.length != 2) None
    else try {
      val rexpr="([A-C])([0-3])".r
      val l = rexpr.findAllIn(code)
      if (l.hasNext) {
        val x = l.group(0).charAt(0) - 'A'
        val y = l.group(1).charAt(0) - '0'
        val xy = (x.toInt, y.toInt)
        if (x < 3 && x >= 0 && y >= 0 && y < 3) {
          Some(xy)
        } else None
      } else None
    } catch {
      case ex: Exception =>
        // AA!!!! --- ex must be printed here.
        ex.printStackTrace()
        None
    }

  }

  def charToPos(ch:Char):Int =
    ch match {
      case 'A' | '0' => 0
    }

}
