package com.codespace.maxim.dscope

/**
  * Created Maxim Sambulat
  */
object Main {
  def main(args: Array[String]): Unit = {
    Context {
      print("Hello")
      scope(exit) {
        println("exit")
      }
      println(" World")
      scope(success){ println("Ok")}
    }
  }
}
