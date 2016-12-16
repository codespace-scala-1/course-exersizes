import yuri.phonecode.WordsLookup

import scala.collection.mutable.ListBuffer
import scala.util.Try

object MainCmd {

  def main(args: Array[String]): Unit = {

    val getCurrentDirectory = new java.io.File(".").getCanonicalPath
    println(getCurrentDirectory)

    import scala.io.Source
    //val source = Source.fromFile("data/test.w")
    val sourceT = Source.fromFile("data/test.t")

    val wordsLookup = new WordsLookup("data/test.w")

    val lookup = wordsLookup.lookup

    for ((k, v) <- lookup) {
      println(s"$k --> ${v.mkString(", ")}")
    }

    println()

    for (testLine <- sourceT.getLines()) {
      val cleanedLine = testLine.replaceAll("[/-]", "")

      val encodings = getEncodings(cleanedLine, true)

      for (enc <- encodings)
        println(s"$testLine: $enc")
    }

    def getEncodings(s: String, allowSkip: Boolean): Seq[String] = {

      def getSplitEncodings(i: Int): Seq[String] = {
        val left = s.substring(0, i)
        val right = s.substring(i)

        lookup.get(left) match {
          case Some(lft) => {
            val rightEncodings = getEncodings(right, true)
            lft.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))
          }

          case _ => if (left.tail.isEmpty && allowSkip) getEncodings(right, false).map(r => (left + " " + r).trim)
                     else Seq()
        }
      }

      if (s.isEmpty) Seq("")
      else {
        val (digitPart, letterPart) = (1 to s.length)
                                          .flatMap(getSplitEncodings)
                                          .partition(_.matches("^[0-9] .+"))

        if (letterPart.isEmpty) digitPart
        else letterPart
      }
    }

  }

}
