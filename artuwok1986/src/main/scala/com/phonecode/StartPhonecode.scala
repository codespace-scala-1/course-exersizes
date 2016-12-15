package com.phonecode

object StartPhonecode {

  def main(args: Array[String]): Unit = {
    val testW: String = scala.io.StdIn.readLine()
    val testT: String = scala.io.StdIn.readLine()

    val fileWords = io.Source.fromFile(testW).getLines.toList
    fileWords.foreach(println)

    val fileTel = io.Source.fromFile(testT).getLines.toList
    fileTel.foreach(println)

    val encoder = new Encoder()
    fileTel.foreach(x => println(encoder.numberToChars(x)))
    fileTel.foreach(x => println(encoder.toWord(encoder.numberToChars(x))))
  }
}
