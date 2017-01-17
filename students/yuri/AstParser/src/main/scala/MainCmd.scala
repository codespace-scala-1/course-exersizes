import java.util.StringTokenizer

import yuri.calculator._

object MainCmd {
  def main(args: Array[String]): Unit = {

    val rexpr="""((-?\d+)(\.\d+)?)(\s+([+*-])\s+(.+))?""".r

    val testString = "-123.4785  + 3.7* 5.1";
    //val testString = "123.4785";
    //val l = rexpr.findAllIn(testString)

    //println(s"left: ${l.group(1)}")
    //println(s"ext_right: ${l.group(4)}")
    //println(s"op: ${l.group(5)}")
    //println(s"right: ${l.group(6)}")


    val p = new Parser

    val res = p.parse(testString)

    println(s"res: ${res.right.get}")
    println()

    val t = new Tokenizer
    //val tokens = t.tokenize("123.4785  + 3.7* 5.1")
    val tokens = t.tokenize("123.4785  * 3.7 + 5.1")

    println(tokens)

    val parseTree = p.parseLevelOne(tokens)

    println(parseTree)
  }
}
