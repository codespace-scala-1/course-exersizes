import org.scalatest._
import yuri.conway.game._

class GameOfLifeTest extends FunSuite {

  test("count neighbours works fine for Glider") {
    val f = new GliderField(10, 10)
    assert(f.getAliveNeighbours(1, 2) === 1, "cell 1,2")
    assert(f.getAliveNeighbours(2, 2) === 5, "cell 2,2")
    assert(f.getAliveNeighbours(2, 3) === 3, "cell 2,3")
    assert(f.getAliveNeighbours(1, 3) === 2, "cell 1,3")
    assert(f.getAliveNeighbours(4, 2) === 3, "cell 4,2")
  }

  test("first generation works fine for Glider") {
    var f:LifeField = new GliderField(10, 10)
    f = f.next().right.get
    assert(f.get(4, 2) === Right(Alive), "cell 4,2 should born")
    assert(f.get(2, 1) === Right(Alive), "cell 2,1 should born")
    assert(f.get(2, 3) === Right(Alive), "cell 2,3 should keep alive")
    assert(f.get(3, 2) === Right(Alive), "cell 3,2 should keep alive")
    assert(f.get(3, 3) === Right(Alive), "cell 3,3 should keep alive")
    assert(f.get(3, 1) === Right(Dead), "cell 3,1 should die")
    assert(f.get(2, 2) === Right(Dead), "cell 2,2 should die")
  }
}