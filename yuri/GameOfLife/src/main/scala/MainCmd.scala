import yuri.conway.game.TenRules
import yuri.conway.game.Field

object MainCmd {

  def main(args: Array[String]): Unit = {

    val rules = TenRules
    val field = rules.emptyField

    field.dump()
    println()

    var f2 = field.acorn()
    f2.dump()
    println()

    var c = 0

    while(c < 30) {

      f2 = f2.next().right.get
      f2.dump()
      println()
      c += 1
    }
  }

}
