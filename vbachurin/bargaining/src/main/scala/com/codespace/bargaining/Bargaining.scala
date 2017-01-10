package com.codespace.bargaining


sealed trait Role {

  override def toString = {
    getClass.toString.replaceAll(".+bargaining.", "")
  }
}

case object Acceptor extends Role
case object Proposer extends Role

object Bargaining {
  def play(a: Player, b: Player, sum: Int): (RoundResult, RoundResult) = {
    val (proposed, newA0) = a.strategy.propose(a, b, sum)
    val (isAccepted, newB) = b.strategy.isAccept(b, a, proposed, sum)
    if (isAccepted) {
      (RoundResult(a.copy(strategy = newA0.proposeResult(true)), Proposer, sum - proposed, sum),
        RoundResult(b.copy(strategy = newB), Acceptor, proposed, sum))
    } else {
      (RoundResult(a.copy(), Proposer, 0, sum),
        RoundResult(b, Acceptor, 0, sum))
    }

  }
}

case class RoundResult(player: Player, role: Role, outcome: Int, sum: Int) {
  override def toString: String = {
    s"Player ${player.id} acting as $role got $outcome of $sum"
  }
}
