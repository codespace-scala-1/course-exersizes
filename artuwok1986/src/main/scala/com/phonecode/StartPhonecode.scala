package com.phonecode

import com.od.Solution2

object StartPhonecode {

  def main(args: Array[String]): Unit = {
    // val testW: String = scala.io.StdIn.readLine()
    // val testT: String = scala.io.StdIn.readLine()

    val testW: String = "/mnt/01D0AD212457E350/Programming/_scala/scala_projects/codespace" +
      "/course-exersizes/artuwok1986/src/main/scala/com/phonecode/test.w"

    val testT: String = "/mnt/01D0AD212457E350/Programming/_scala/scala_projects/codespace" +
      "/course-exersizes/artuwok1986/src/main/scala/com/phonecode/test.t"

    val fileWords = io.Source.fromFile(testW).getLines.toList
    fileWords.foreach(println)

    val fileTel = io.Source.fromFile(testT).getLines.toList
    fileTel.foreach(println)


    val z = new Solution2(fileWords)
    println(z.encode("4824"))

    //val encoder = new Encoder()
    //println(encoder.phoneNumberToChars(fileTel))
    //println(encoder.mappingToWord(encoder.phoneNumberToChars(fileTel)))
  }
}
