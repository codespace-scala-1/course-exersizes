package  com.codespace.maxim.dscope

import org.scalatest._

class DScopeTest extends FunSuite
{

  test("call exit on normal endinf") {
   Context { 
      print("Hello")
      scope(exit) {
        println("exit")
      }
      println(" World")
      scope(success){ println("Ok")
      }
    }
  }

}
