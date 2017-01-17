package codespace.phonecode

import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {
    // read file with codes
    val phonecode = Source.fromFile("/Users/johnsmith/IdeaProjects/course-exersizes/vukelan/PhoneCode/data/phonecode.txt", "UTF-8")
    val linesPhonecode = phonecode.getLines
    val numPattern = "[0-9]+".r
    val letPattern = "[a-z,A-Z]+".r

    // map characters to digits
    val charToDigit = linesPhonecode
      .map(
        x => (
          numPattern.findFirstIn(x).getOrElse(""),
          letPattern.findAllIn(x).mkString
        )
      )
      .flatMap(
        x => for (c <- x._2; n = x._1) yield c -> n.toInt
      )
      .toMap

    // read words and transforming them to numbers
    val words =
      Source
        .fromFile("/Users/johnsmith/IdeaProjects/course-exersizes/vukelan/PhoneCode/data/test.w")
        .getLines()
          .toArray

    val words2 = words


    val wordsTranslated = words
      .map(word =>
        (word, for (char <- word.replace(""""""","").toLowerCase()) yield charToDigit.getOrElse(char, "+"))
      )
      .map(x => (x._1, x._2.mkString))
      .filter(!_._2.contains("+"))
//      .map(x => (x._2, x._1))
      .toMap
    // output test
    // println(wordsTranslated.mkString("--\n"))

    val reducedWords = words2
      .map(word =>
        (word, for (char <- word.replace(""""""","").toLowerCase()) yield charToDigit.getOrElse(char,"+"))
      )
      .map(x => (x._1, x._2.mkString))
      .filter(!_._2.contains("+"))
      .toMap
    println("*******")
    println(reducedWords)


    // ***---***
    // read telephone numbers
    val numbers = Source.fromFile("/Users/johnsmith/IdeaProjects/course-exersizes/vukelan/PhoneCode/data/test.t")
      .getLines()

    val translatedNumbers = numbers
      .map(
        x => (
          x.replace("-", "").replace("/", ""),
          wordsTranslated.getOrElse(x.replace("-", "").replace("/", ""), "-")
        )
      )
    println(translatedNumbers.mkString("\n"))
    println(wordsTranslated.mkString(" "))

    // another map format (word,number)--(next_word..n,next_number..n)
    val codes = words
      .flatMap(x => for (i <- x) yield (x, charToDigit.getOrElse(i, "+")))
    //    println(codes.mkString("--"))

    // unfinished for comprehension variant
    val digitLetters = for (l <- linesPhonecode) {
      val digit = numPattern.findFirstIn(l)
      val letters = letPattern.findAllIn(l)
    }

    // print all matches-digits
    val matches = for (m <- numPattern.findAllIn(linesPhonecode.mkString)) yield m
    System.out.println(matches.mkString("->"))

    // print all lines
    for (line <- linesPhonecode) {
      System.out.println(line)
    }

  }
}
