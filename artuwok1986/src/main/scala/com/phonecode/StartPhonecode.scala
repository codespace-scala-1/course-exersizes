package com.phonecode

object StartPhonecode {

  def main(args: Array[String]): Unit = {

    val phoneNumber: String = scala.io.StdIn.readLine()
    val encoder = new Encoder()
    println(encoder.numberToChars(phoneNumber))
    println(encoder.toWord(encoder.numberToChars(phoneNumber)))
  }
}
