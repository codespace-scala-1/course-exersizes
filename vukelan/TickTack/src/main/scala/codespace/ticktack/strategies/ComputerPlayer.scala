package codespace.ticktack.strategies
import codespace.ticktack._
import codespace.ticktack.ThreeRules._

class ComputerPlayer(label: Label, rules: Rules) extends BasePlayer(label, rules) {

  def nextStep(f: Field): Either[String, ((Int,Int),Player)] = ???

  /*
  method nextStep2 first call defence on the given field, then tries to attack
   */
  def nextStep2(f: ThreeField): Either[String, (Int, Int)] = {
    if (defence(f) != (-1,-1)) Right(defence(f))
    else Right(attack(f))
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

    // helper "defenceDiagonal" gives (Int, Int) of postion to block
    def defenceDiagonal(f: ThreeField): (Int, Int) = {

      def defenceLeftDiagonal: (Int, Int) = {
        val leftDiagonal = for (i <- 0 to 2; j <- 0 to 2 if j == i) yield
          f.get(i, j)
        leftDiagonal match {
          case IndexedSeq(`opponentLabel`, `opponentLabel`, None) => (2, 2)
          case IndexedSeq(`opponentLabel`, None, `opponentLabel`) => (1, 1)
          case IndexedSeq(None, `opponentLabel`, `opponentLabel`) => (0, 0)
          case _ => (-1, -1)
        }
      }

      def defenceRightDiagonal: (Int, Int) = {
        val rightDiagonal = for (i <- 0 to 2; j <- 0 to 2 if j == i) yield
          f.get(i, 2 - j)
        rightDiagonal match {
          case IndexedSeq(`opponentLabel`, `opponentLabel`, None) => (0, 2)
          case IndexedSeq(`opponentLabel`, None, `opponentLabel`) => (1, 1)
          case IndexedSeq(None, `opponentLabel`, `opponentLabel`) => (2, 0)
          case _ => (-1, -1)
        }
      }

      if (defenceLeftDiagonal != (-1, -1)) defenceLeftDiagonal
      else if (defenceRightDiagonal != (-1, -1)) defenceRightDiagonal
      else (-1, -1)

    }

    // "defencreRow" neet to work with transponent version of f: ThreeFiels
    var transponent = for (i <- 0 to 2) yield {
      for (j <- 0 to 2) yield {
        f.get(j, i)
      }
    }

    val rowsToDefence = for (i <- 0 to 2;
                             if defenceRow(f.data(i)) != -1)
      yield (i, defenceRow(f.data(i)))

    val columnsToDefens =
      for (i <- 0 to 2;
           column = defenceRow(transponent(i))
           if column != -1) yield (column, i)

    if (rowsToDefence.nonEmpty) rowsToDefence.head
    else if (columnsToDefens.nonEmpty) columnsToDefens.head
    else if (defenceDiagonal(f) != (-1, -1)) defenceDiagonal(f)
    else (-1, -1)

  }

  /*
     at first, "attack" tries to complete line to win,
     then, "attack" attempt to make triangle on the field to guarantee victory to computer
     for example field (3,1) and (1,3) cannot be blocked at one step. Computer wins.
       - - x
       - x -
       - - x
  */
  def attack(f: ThreeField): (Int, Int) = {
    /*
    helper complete looks for almost ready line to make winning step
     */
    def complete(f: ThreeField): (Int, Int) = {
      // helper defenceRow gives position of field that needs to be blocked
      def completeRow(i: IndexedSeq[Option[Label]]): Int = {
        i match {
          case IndexedSeq(`label`, `label`, None) => 2
          case IndexedSeq(`label`, None, `label`) => 1
          case IndexedSeq(None, `label`, `label`) => 0
          case _ => -1
        }
      }

      // helper "defenceDiagonal" gives (Int, Int) of postion to block
      def completeDiagonal(f: ThreeField): (Int, Int) = {

        def completeLeftDiagonal: (Int, Int) = {
          val leftDiagonal = for (i <- 0 to 2; j <- 0 to 2 if j == i) yield
            f.get(i, j)
          leftDiagonal match {
            case IndexedSeq(`label`, `label`, None) => (2, 2)
            case IndexedSeq(`label`, None, `label`) => (1, 1)
            case IndexedSeq(None, `label`, `label`) => (0, 0)
            case _ => (-1, -1)
          }
        }

        def completeRightDiagonal: (Int, Int) = {
          val rightDiagonal = for (i <- 0 to 2; j <- 0 to 2 if j == i) yield
            f.get(i, 2 - j)
          rightDiagonal match {
            case IndexedSeq(`label`, `label`, None) => (0, 2)
            case IndexedSeq(`label`, None, `label`) => (1, 1)
            case IndexedSeq(None, `label`, `label`) => (2, 0)
            case _ => (-1, -1)
          }
        }

        if (completeLeftDiagonal != (-1, -1)) completeLeftDiagonal
        else if (completeRightDiagonal != (-1, -1)) completeRightDiagonal
        else (-1, -1)

      }

      // "completeRow" need to work with transponent version of f: ThreeFiels
      var transponent = for (i <- 0 to 2) yield {
        for (j <- 0 to 2) yield {
          f.get(j, i)
        }
      }

      val rowsToComplete = for (i <- 0 to 2;
                                if completeRow(f.data(i)) != -1)
        yield (i, completeRow(f.data(i)))

      val columnsToComplete =
        for (i <- 0 to 2;
             column = completeRow(transponent(i))
             if column != -1) yield (column, i)

      if (rowsToComplete.nonEmpty) rowsToComplete.head
      else if (columnsToComplete.nonEmpty) columnsToComplete.head
      else if (completeDiagonal(f) != (-1, -1)) completeDiagonal(f)
      else (-1, -1)

    }

    /* "makeSplit" step to center,
    then step to corner cells
    for example:
        x - x
        - x -
        x - x
     */
    def makeSplit: IndexedSeq[(Int, Int)] = {
      if (f.get(1, 1).isEmpty) IndexedSeq((1, 1))
      else {
        val cornerCells = for (i <- 0 to 2 if i % 2 == 0; j <- 0 to 2 if j % 2 == 0) yield {(i, j)}
        for (i <- cornerCells if f.get(i._1,i._2).isEmpty) yield (i._1,i._2)
      }
    }

    if (complete(f: ThreeField) != (-1, -1)) complete(f: ThreeField)
    else makeSplit.head

  }

  override def tell(s: String) = this

}
