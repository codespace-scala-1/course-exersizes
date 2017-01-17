package com.od

object StartPhonecode {

  def main(args: Array[String]): Unit = {

    val rootDir = args(0)

    val testW: String =  rootDir + "/src/main/scala/com/od/test.w"

    val testT: String = rootDir + "src/main/scala/com/od/test.t"

    val fileWords = io.Source.fromFile(testW).getLines.toList
    fileWords.foreach(println)

    val fileTel: List[String] = io.Source.fromFile(testT).getLines.toList
    fileTel.foreach(println)

    val z = new Solution2(fileWords)
    fileTel.foreach(x => println(z.encode(x)))


  }
}
