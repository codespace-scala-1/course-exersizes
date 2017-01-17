import scala.io.Source

val map = Map(1 -> ('a', 'b', 'c'), 2 -> ('d', 'e', 'f'))
val numPattern = "[0-9]+".r
val letPattern = "[a-z,A-Z]+".r

// mac os
// val phonecode = Source.fromFile("/Users/johnsmith/IdeaProjects/course-exersizes/vukelan/PhoneCode/data/phonecode.txt", "UTF-8")

// windows
val phonecode = Source.fromFile("D:\\IdeaProjects\\course-exersizes\\vukelan\\PhoneCode\\data\\phonecode.txt", "UTF-8")

val lines = phonecode.getLines
lines.map(
  x => (
    numPattern.findFirstIn(x).getOrElse(""),
    letPattern.findAllIn(x).mkString
  ))
    .flatMap(
      x => for (i <- x._2; n = x._1) yield i -> n.toInt
    ).toMap
//  .foldLeft(Map[Char,Int])(_)
//  .foreach(println)

def function() = {
  ???
}

var lettersEfg = List('e','f','g')
var number1 = List(1)
lettersEfg.zipWithIndex

val ar = Array('c','b', "dd")
ar.foreach(println)

lines.map(
  x =>
    (letPattern.findAllIn(x).toList,
    numPattern.findFirstIn(x).toList)
)
  .foreach(println)



val m = for (i <- lines;
             c = letPattern.findAllIn(i).toList;
             n = numPattern.findFirstIn(i).getOrElse("")) yield for (i <- c) yield (i, n)

m.foreach(println)
//m.flatMap(x => x._1.zip(x._2)).foreach(println)


val lettersToDigits3 = phonecode
  .getLines()
  .map(a =>
    (letPattern
      .findAllIn(a)
      .toList,
    numPattern
      .findAllIn(a)
        .toList
    )
  ).map(
  x => x._1.zip(x._2)
)


lettersToDigits3.foreach(println)

val lettersToDigits = phonecode
.getLines()
  .map(
    a => letPattern
      .findAllIn(a)
        .toList ->
    numPattern
      .findFirstIn(a)
  )
lettersToDigits.foreach(println)

val lettersToDigits2 = lettersToDigits
  .map(a =>
  a._1 -> a._2)

lettersToDigits2.foreach(println)

val digitToLetters = phonecode
  .getLines
  .map(a => numPattern
    .findFirstIn(a)
    .getOrElse(" ") ->
    letPattern
      .findAllIn(a)
      .toList
  )

digitToLetters.foreach(println)


val chars = new Array[Char](100)

chars.length
chars.update(0, 'a')
chars(0)
chars(1)
chars
chars.length

for (i <- map) print(i._1)
for (i <- map) print(i._2._1)
