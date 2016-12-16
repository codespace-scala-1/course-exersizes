import scala.collection.mutable.ListBuffer

val res = "abcdef".flatMap(c => (c + "."))


val ab = ListBuffer("1 abcdef lfjjhfhg", "fgh fggh")

val (v, w) = ab.partition(x => x.matches("^[0-9] .+"))

val leftEncodings = ListBuffer("a", "b", "c")
//val leftEncodings = ListBuffer[String]()
val rightEncodings = ListBuffer("1", "2", "3")
//val rightEncodings = ListBuffer("")

val z = leftEncodings.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))

Seq(leftEncodings, rightEncodings).flatten