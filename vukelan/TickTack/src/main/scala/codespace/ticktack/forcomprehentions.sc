import codespace.ticktack._

val (c, t, n) = (Some(CrossLabel), Some(ToeLabel), None)
val opponentLabel = Some(ToeLabel)

val data = IndexedSeq(
  IndexedSeq(t, t, n),
  IndexedSeq(c, t, n),
  IndexedSeq(c, n, n)
)
val trans = for (i <- 0 to 2) yield {
  for (j <- 0 to 2) yield {
    data(j)(i)
  }
}
trans(2)(0)
data(1)(1)

def defenceRow(row: IndexedSeq[Option[Label]]): Int = {
  row match {
    case IndexedSeq(`opponentLabel`, `opponentLabel`, None) => 2
    case IndexedSeq(`opponentLabel`, None, `opponentLabel`) => 1
    case IndexedSeq(None, `opponentLabel`, `opponentLabel`) => 0
    case _ => -1
  }
}

val rows = for (i <- 0 to 2 if defenceRow(data(i)) != -1) yield (i, defenceRow(data(i)))

rows.nonEmpty
rows.head

val cols = for (i <- 0 to 2 if defenceRow(trans(i)) != -1) yield (defenceRow(trans(i)),i)



val f = ThreeRules.ThreeField(data)

println("***")

val transponent = for (i <- 0 to 2) yield {
  for (j <- 0 to 2) yield {
    f.get(j, i)
  }
}

//def defenceRow(row: IndexedSeq[Option[Label]]): Int = {
//
//}

//CrossPlayer.nextStep2(f)

object CrossPlayer extends Player {
  val rules = ThreeRules
  val label = CrossLabel

  //  def nextStep2(f: Field): Map[String -> Some[Label]] = {
  //    for (i <- 0 to 2) {
  //      for (j <- 0 to 2) yield {i.toString + j.toString -> f.get(i,j)}
  //    }
  //  }

  override def nextStep(f: Field): Either[String, ((Int, Int), Player)] = {
    Left("wrong!")
    Right((1, 1), this)
  }

  def nextStep1(f: Field): Either[String, ((Int, Int), Player)] = {
    f.get(0, 0) match {
      case Some(ToeLabel) => Left("Wrong!")
      case Some(CrossLabel) => Right(((0, 0), this))
      case None => Left("still wrong")
    }
  }

  override def tell(s: String) = ???
}
