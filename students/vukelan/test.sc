import codespace.ticktack.{CrossLabel, ToeLabel}
import codespace.ticktack._


val (t, c, n) = (Some(ToeLabel), Some(CrossLabel), None)

val data = IndexedSeq(
  IndexedSeq(t, t, n),
  IndexedSeq(n, t, t),
  IndexedSeq(t, n, t))

def defenceRow(row: IndexedSeq[Option[Label]]): Int = {
  row match {
    case IndexedSeq(`t`, `t`, `n`) => 2
    case IndexedSeq(`n`, `t`, `t`) => 0
    case IndexedSeq(`t`, `n`, `t`) => 1
    case _ => -1
  }
}

val transparent = for (i <- 0 to 2) yield {
  for (j <- 0 to 2) yield {
    data(j)(i)
  }
}

//***

for (i <- transparent) yield defenceRow(i)


//for (i <- data) yield defenceRow(i)