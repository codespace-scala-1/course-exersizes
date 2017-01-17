package com.codespace.bargaining.strategies

import com.codespace.bargaining.Player

trait PlayerStrategy {

  def isAccept(me: Player, otherParty: Player, percent: Int, sum: Int): (Boolean, PlayerStrategy)

  def propose(me: Player, otherParty: Player, percent: Int): (Int, PlayerStrategy)

  def proposeResult(result: Boolean): PlayerStrategy
}
