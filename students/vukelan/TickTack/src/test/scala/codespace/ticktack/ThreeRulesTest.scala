package codespace.ticktack
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class ThreeRulesTest extends FunSuite
  with BeforeAndAfterAll {

  override def beforeAll() = {
    Console.println("Hi")
  }

  test("set field actually set field") {
    val rules = ThreeRules
    val ef = rules.emptyField
    val f1 = ef.put(0, 0, CrossLabel).right.get
    assert(f1.get(0, 0) === Some(CrossLabel))
  }

  test("impossible to get field at 10,10") {
    val rules = ThreeRules
    val f = rules.emptyField
    assertThrows[IllegalArgumentException] {
      f.get(10, 10)
    }
  }

  test("check thet win is win") {
    val (c, t, n) = (Some(CrossLabel), Some(ToeLabel), None)
    val data = IndexedSeq(
      IndexedSeq(c, n, t),
      IndexedSeq(n, c, t),
      IndexedSeq(t, n, c)
    )
    val r = ThreeRules
    var f = new r.ThreeField(data)
    r.isWin(f).contains(c)
  }


}
