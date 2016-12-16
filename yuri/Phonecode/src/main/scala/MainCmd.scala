import scala.collection.mutable.ListBuffer
import scala.util.Try

object MainCmd {

  def main(args: Array[String]): Unit = {

    val getCurrentDirectory = new java.io.File(".").getCanonicalPath
    println(getCurrentDirectory)

    import scala.io.Source
    val source = Source.fromFile("data/test.w")
    val sourceT = Source.fromFile("data/test.t")

    val lineIterator = source.getLines

    def encodeChar(c: Char): Option[String] = Try {
      c.toLower match {
        case 'e' => "0"
        case 'j' | 'n' | 'q' => "1"
        case 'r' | 'w' | 'x' => "2"
        case 'd' | 's' | 'y' => "3"
        case 'f' | 't' => "4"
        case 'a' | 'm' => "5"
        case 'c' | 'i' | 'v' => "6"
        case 'b' | 'k' | 'u' => "7"
        case 'l' | 'o' | 'p' => "8"
        case 'g' | 'h' | 'z' => "9"
      }
    }.toOption

    var lookup = Map[String, List[String]]()

    for (rawLine <- lineIterator) {
      val encoding = rawLine.replaceAll("\"", "").flatMap(c => encodeChar(c).get)

      val newList = lookup.get(encoding) match {
        case None => List(rawLine)
        case Some(prevAliases) => prevAliases :+ rawLine
      }

      lookup += (encoding -> newList)
    }

    for ((k, v) <- lookup) {
      println(s"$k --> ${v.mkString(", ")}")
    }

    println()

    for (testLine <- sourceT.getLines()) {
      val cleanedLine = testLine.replaceAll("[/-]", "")

      var encodings = getEncodings(cleanedLine, true)

      for (enc <- encodings)
        println(s"$testLine: $enc")
    }

    def getEncodings(s: String, allowSkip: Boolean): ListBuffer[String] = {
      var result = ListBuffer[String]()

      for (i <- 1 to s.length) {
        val left:String = s.substring(0, i)
        val right:String = s.substring(i)

        var leftEncodings: Option[List[String]] = None
        var skipWasMade = false

        leftEncodings = lookup.get(left)

        if (leftEncodings.isEmpty && ((i == 1) && allowSkip)) {
          leftEncodings = Some(List[String](left))
          skipWasMade = true
        }

        if (!leftEncodings.isEmpty && leftEncodings.get.length > 0) {
          if (right == "") {
            result ++= leftEncodings.get
          }
          else {
            var rightEncodings = getEncodings(right, !skipWasMade)

            if (i == s.length - 1 && !skipWasMade)
              rightEncodings += right

            for (s2 <- rightEncodings)
              result ++= leftEncodings.map(s1 => s1 + " " + s2)

            //result ++= for (s2 <- rightEncodings; s1 <- leftEncodings)  yield (s1 + " " + s2)
             // result ++= leftEncodings.map(s1 => s1 + " " + s2)
          }
        }
      }

      result.distinct
    }

  }

}
