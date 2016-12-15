package codespace.ticktack.strategies

import codespace.ticktack.ThreeRules.ThreeField
import codespace.ticktack._

class ComputerPlayer(label: Label, rules: Rules) extends BasePlayer(label, rules) {

  def nextStep(f: ThreeField): Either[String, ((Int, Int), Player)] = {
    // read field
    // return ((Int,Int),Player)
  }

  // determine what is the opponent's label
  val opponentLabel: Label = label match {
    case ToeLabel => CrossLabel
    case CrossLabel => ToeLabel
  }

  /*
     "defence" blocks danger field's cells
     for example it blocks field (3,1)
      0 - -
      0 - -
      x - -
  */
  def defence(f: ThreeField): (Int, Int) = {
    // helper defenceRow gives position of field that needs to be blocked
    def defenceRow(i: IndexedSeq[Option[Label]]): Int = {
      i match {
        case IndexedSeq(`opponentLabel`, `opponentLabel`, None) => 2
        case IndexedSeq(`opponentLabel`, None, `opponentLabel`) => 1
        case IndexedSeq(None, `opponentLabel`, `opponentLabel`) => 0
        case _ => -1
      }
    }

    // "defencreRow" neet to work with transponent version of f: ThreeFiels
    var transponent = for (i <- 0 to 2) yield {
      for (j <- 0 to 2) yield {
        f.get(j, i)
      }
    }

    val rowsToDefence = for (i <- 0 to 2; if defenceRow(f.data(i)) != -1) yield (i, defenceRow(f.data(i)))

    val columnsToDefens =
      for (i <- 0 to 2;
           column = defenceRow(transponent(i))
           if column != -1) yield (column, i)

    if (rowsToDefence.nonEmpty) rowsToDefence(0)
    else if (columnsToDefens.nonEmpty) columnsToDefens(0)
    else (-1,-1)

  }

  /*
     "attack" attempt to make triangle on the field to guarantee victory to computer
     for example field (3,1) and (3,3) cannot be blocked at one step. Computer wins.
       - - x
       - x x
       - - -
  */
  def attack(f: Field): (Int, Int) = {

  }

  override def tell(s: String) = ???

}
