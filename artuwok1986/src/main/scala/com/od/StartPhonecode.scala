package com.od

object StartPhonecode {

  def main(args: Array[String]): Unit = {

    val testW: String = "/mnt/01D0AD212457E350/Programming/_scala/scala_projects" +
      "/codespace/course-exersizes/artuwok1986/src/main/scala/com/od/test.w"

    val testT: String = "/mnt/01D0AD212457E350/Programming/_scala/scala_projects" +
      "/codespace/course-exersizes/artuwok1986/src/main/scala/com/od/test.t"

    val fileWords = io.Source.fromFile(testW).getLines.toList
    fileWords.foreach(println)

    val fileTel: List[String] = io.Source.fromFile(testT).getLines.toList
    fileTel.foreach(println)

    val z = new Solution2(fileWords)
    fileTel.foreach(x => println(z.encode(x)))


  }
}
