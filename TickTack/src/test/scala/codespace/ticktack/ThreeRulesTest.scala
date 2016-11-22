package codespace.ticktack

import org.scalatest._

class ThreeRulesTest extends FunSuite
                       with BeforeAndAfter
{

   before{
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
     assertThrows[NoSuchElementException] {
       f.get(10,10)
     }
   }



}
