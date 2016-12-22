import scala.io.Source

// read encoding to array
val codes = Source
  .fromFile("D:\\IdeaProjects\\course-exersizes\\vukelan\\PhoneCode\\data\\phonecode.txt")
  .getLines()
  .toArray

// regex matching object for letters and digits
val numPattern = "[0-9]".r
val letPattern = "[a-z,A-Z]+".r

// char to digit encoding
val charToDigit = codes
  .map(x =>
    (
      numPattern.findFirstIn(x),
      letPattern.findAllIn(x)
    )
  )
  .map(x =>
    (
      x._2.mkString,
      x._1.getOrElse("")
    )
  ).toMap

//"string".contains('c')
charToDigit.find(_._1.contains('a')).getOrElse(2,"")
// output
charToDigit.foreach(println)


// read words to array
val words = Source
  .fromFile("D:\\IdeaProjects\\course-exersizes\\vukelan\\PhoneCode\\data\\test.w")
  .getLines()
  .toArray

// read numbers to array
val numbers = Source
  .fromFile("D:\\IdeaProjects\\course-exersizes\\vukelan\\PhoneCode\\data\\test.t")
  .getLines()
  .toArray
  .map(x =>
    x.replace("/", "")
      .replace("-", "")
  )

// create words to digits Map
val wordsToDigits = words
    .map(
      x => (
        stringToDigits(x),
        x
      )
    )
    .groupBy(_._1)


def stringToDigits(s:String):String = {
  s
    .replace(""""""","")
    .replace("/","")
    .toLowerCase()
    .map(c => charToDigit
    .find(x => x._1.contains(c))
    .getOrElse("+"))
      .map {
        case (_,v) => v
        case _ =>
      }
    .mkString
}

stringToDigits("7bal")

val s = Some("bb",1)
s.foreach {
  case (_, v) => println(v)
}