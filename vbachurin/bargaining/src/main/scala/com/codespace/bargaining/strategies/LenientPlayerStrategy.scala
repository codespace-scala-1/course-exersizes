package com.codespace.bargaining.strategies

import com.codespace.bargaining.Player

object LenientPlayerStrategy extends PlayerStrategy {
  override def isAccept(me: Player, otherParty: Player, percent: Int, sum: Int): (Boolean, PlayerStrategy) = {
    if (percent > 0)
      (true, this)
    else
      (false, this)
  }

  override def propose(me: Player, otherParty: Player, percent: Int): (Int, PlayerStrategy) = {
    (50, this)
  }

  override def proposeResult(result: Boolean): PlayerStrategy = this
}
