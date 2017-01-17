package com.codespace.bargaining.strategies

import com.codespace.bargaining.Player

object GreedyPlayerStrategy extends PlayerStrategy {

  override def isAccept(me: Player, otherParty: Player, percent: Int, sum: Int): (Boolean, PlayerStrategy) = {
    if (percent < 50)
      (false, this)
    else
      (true, this)
  }

  override def propose(me: Player, otherParty: Player, percent: Int): (Int, PlayerStrategy) = {
    (30, this)
  }

  override def proposeResult(result: Boolean): PlayerStrategy = {
    this
  }
}
