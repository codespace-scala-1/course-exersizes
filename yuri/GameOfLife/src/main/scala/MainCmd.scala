import yuri.conway.game._

import scala.io.StdIn._
import scala.util.Try

object MainCmd {

  def main(args: Array[String]): Unit = {

    var patternSelected:Option[Int] = None

    do {
      print("Enter desired pattern (1 - Acorn, 2 - Random, 3 - Glider): ")
      patternSelected = Try(readLine().toInt).toOption.filter(x => x>=1 && x<=3)

      if(patternSelected.isEmpty)
        println("Wrong number\r\n")

    } while(patternSelected.isEmpty)

    var f:LifeField = {

      patternSelected.get match {
        case 1 => new AcornField(50, 20)
        case 2 => new RandomField(50, 20)
        case 3 => new GliderField(10, 10)
      }
    }

    f.dump()
    println()

    var c = 0

    while(c < 50) {

      f = f.next().right.get
      f.dump()
      println()
      c += 1
    }
  }

}
