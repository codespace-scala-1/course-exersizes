import yuri.conway.game.{Alive, CellState, Dead}

import scala.util.Random

val s = 0 until 10

s.last


val r = Random

//var d: IndexedSeq[IndexedSeq[CellState]] = IndexedSeq[IndexedSeq[CellState]]()

//for(i <- data.indices; j <- data.indices) {
//  if(r.nextDouble() > 0.5) Alive else Dead
//}

val ar = Array.tabulate[Int](100,6) { (i,j) => if(r.nextDouble() > 0.5) 1 else 0 }

val ar1 = Array.fill[Int](6,6) { 10 }

ar(0).toList


ar(0)(0) = 12
ar(0).toList

var ar22 = ar.clone()
ar22(0)(1) = 17
ar22(0).toList

ar(0).toList


//(0 until 10).toList

