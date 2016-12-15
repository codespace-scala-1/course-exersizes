import org.scalatest._
import yuri.conway.game.TenRules

class GameOfLifeTest extends FunSuite {

  test("count neighbours works fine") {
    val rules = TenRules
    val field = rules.emptyField
    val f2 = field.glider()
    assert(f2.getAliveNeighbours(1, 2) === 1, "cell 1,2")
    assert(f2.getAliveNeighbours(2, 2) === 5, "cell 2,2")
    assert(f2.getAliveNeighbours(2, 3) === 3, "cell 2,3")
    assert(f2.getAliveNeighbours(1, 3) === 2, "cell 1,3")
    assert(f2.getAliveNeighbours(4, 2) === 3, "cell 4,2")
  }
}