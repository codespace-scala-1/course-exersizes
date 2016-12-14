package codespace.ticktack.strategies
import codespace.ticktack.{Player, Label, Rules, Field}

class ComputerPlayer (label: Label, rules: Rules) extends BasePlayer(label,rules) {

  def nextStep(f: Field):Either[String,((Int,Int),Player)] = {
    Right((1,1),this)
  }

  def attack(f: Field):(Int,Int) = ???

  def defence(f: Field):(Int,Int) = ???

  def tell(s: String): Player = ???

}
