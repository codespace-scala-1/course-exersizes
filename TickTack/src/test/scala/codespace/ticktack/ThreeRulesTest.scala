package codespace.ticktack

import org.scalatest._

class ThreeRulesTest extends FunSuite
                       with BeforeAndAfterAll
{

   override def beforeAll() = {
     Console.println("Hi")
   }

   test("set field actually set field") {
     val rules = new ThreeRules
     val ef = rules.emptyField
     val f1 = ef.put(0,0,CrossLabel)
     assert(f1.get(0,0)===Some(CrossLabel))
   }

   test("impossible to get field at 10,10") {
     val rules = new ThreeRules
     val f = rules.emptyField
     assertThrows[IllegalArgumentException] {
       f.get(10,10)
     }
   }

   test("check thet win is win") {
     val (c,t,n) = (Some(CrossLabel),Some(ToeLabel),None)
     val data = IndexedSeq(
       IndexedSeq(c,n,t),
       IndexedSeq(n,c,t),
       IndexedSeq(t,n,c)
     )
     val r = new ThreeRules
     val r1 = new ThreeRules
     var f = new r.ThreeField(data)
     val f1 = new r1.ThreeField(data)
     //f = f1
     r.isWin(f)==Some(c)
   }



}
