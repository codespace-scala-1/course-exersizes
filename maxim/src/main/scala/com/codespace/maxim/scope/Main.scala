package com.codespace.maxim.dscope

import com.codespace.maxim.scope._

/**
  * Created Maxim Sambulat
  */
object Main {
  def main(args: Array[String]): Unit = {
    Context() { scope =>
      println("============= step 1 =============")
      scope(exit) {  println("exit 1") }
      println("============= step 2 =============")
      scope(exit) {  println("exit 2") }
      println("============= step 3 =============")
      scope(success) {  println("success 1") }
      println("============= step 4 =============")
      scope(success) {  println("success 2") }
      println("============= step 5 =============")
      scope(failure) {  println("failure 1") }
      throw new Exception
      println("============= step 6 =============")
      scope(exit) {  println("exit 3") }
    }
  }
}
