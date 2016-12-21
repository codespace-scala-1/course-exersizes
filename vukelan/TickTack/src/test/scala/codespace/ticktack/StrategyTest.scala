package codespace.ticktack

import codespace.ticktack.ThreeRules.ThreeField
import codespace.ticktack.strategies.ComputerPlayer
import codespace.ticktack.{CrossLabel, ToeLabel}
import org.scalatest.FunSuite

class StrategyTest extends FunSuite {

  // field to test
  val (c, t, n) = (Some(CrossLabel), Some(ToeLabel), None)
  val player = new ComputerPlayer(ToeLabel,ThreeRules)
  val cells = IndexedSeq(IndexedSeq(c, c, t),
                        IndexedSeq(n, n, n),
                        IndexedSeq(t, t, n))
  val field = ThreeField(cells)

  test("computer player blocks danger cells") {
    assert(player.nextStep2(field) == Right(0,2))
  }


}