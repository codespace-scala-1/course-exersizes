package codespace.phonecode

import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {

    val phonecode = Source.fromFile("/Users/johnsmith/IdeaProjects/course-exersizes/vukelan/PhoneCode/data/phonecode.txt", "UTF-8")
    val lineIterator = phonecode.getLines
    val numPattern = "[0-9]+".r
    val letterPattern = "[a-z,A-Z]+".r

    val digitLetters = for (l <- lineIterator) {
      val digit = numPattern.findFirstIn(l)
      val letters = letterPattern.findAllIn(l)
    }

    val m = Map(1 -> ("a", "b"))

    System.out.println(m)

    // find all matches-digits and print them
    val matches = for (m <- numPattern.findAllIn(lineIterator.mkString)) yield m
    System.out.println(matches.mkString("->"))

    // print all lines
    for (line <- lineIterator) {
      System.out.println(line)
    }

  }
}
