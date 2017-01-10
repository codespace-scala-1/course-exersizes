package com.codespace.bargaining

import com.codespace.bargaining.Bargaining.play
import com.codespace.bargaining.strategies.{GreedyPlayerStrategy, LenientPlayerStrategy}

object GameOn {
  def main(args: Array[String]): Unit = {
    val a = Player("Greedy1", 1000, GreedyPlayerStrategy)
    val b = Player("Lenient2", 1000, LenientPlayerStrategy)

    val (rr1, rr2) = play(a, b, 100)
    println(rr1, rr2)
  }
}
