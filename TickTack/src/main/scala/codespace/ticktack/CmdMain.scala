package codespace.ticktack

object CmdMain extends Game {

  val rules = ThreeRules

  def main(args:Array[String]):Unit =
  {
    val aPlayer = new strategies.Recursive(ToeLabel,rules)
    val bPlayer = new strategies.Recursive(CrossLabel,rules)
    val field = play(aPlayer,bPlayer)
    println(field)

    //hello
  }
// another one
}
