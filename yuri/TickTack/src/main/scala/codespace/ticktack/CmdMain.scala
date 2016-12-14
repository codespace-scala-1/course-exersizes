package codespace.ticktack

object CmdMain extends Game {

  val rules = ThreeRules

  def main(args:Array[String]):Unit =
  {
    val aPlayer = new strategies.ConsolePlayer(ToeLabel,rules)
    val bPlayer = new strategies.MachinePlayer(CrossLabel,rules)

    val (f, isWin, lastLabel) = play(aPlayer, bPlayer)

    if(isWin)
      println(s"Game over - WIN of ${lastLabel}")
    else
      println("Game over - DRAW")
  }

}
