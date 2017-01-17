val rexpr="((-?\\d+)(\\.\\d+)?)(\\s+([+-*])\\s+(.+))".r

val q = "-123.4785  + 3.7* 5.1".matches("((-?\\d+)(\\.\\d+)?)\\s+(\\+)\\s+(.+)")

val l = rexpr.findAllIn("-123.4785  + 3.7* 5.1")

l.hasNext

val x = l.group(1)
l.group(2)
//val xy = (x.toInt, y.toInt)

rexpr


