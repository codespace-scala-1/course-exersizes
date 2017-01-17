val rexpr="""((-?\d+)(\.\d+)?)(\s+([+*-])\s+(.+))?""".r

val q = "-123.4785  + 3.7* 5.1".matches("((-?\\d+)(\\.\\d+)?)\\s+(\\+)\\s+(.+)")

val l = rexpr.findAllIn("-123.4785  + 3.7* 5.1")

l.hasNext

val x = l.group(1)
l.group(2)
//val xy = (x.toInt, y.toInt)

//val re2 = rexpr

val pattern1 = """(-?\d+(\.\d+)?)(.+)""".r

val l1 = pattern1.findAllIn("-123.4785  + 3.7* 5.1")

l1.group(1)
l1.group(3)

val tokens = Seq(1, 2, 3, 5, 0, 5, 1, 2)


val beforeFive = tokens.takeWhile(t => t != 5 && t != 0)

//tokens.ind
//tokens.in
val idx = tokens.indexOf(55)
tokens.take(idx)
tokens.drop(idx+1)


val pattern2 = """(-?\d+(\.\d+)?)(.+)""".r
val m = pattern2.findAllIn("5.1")

m.group(1)
m.group(2)
