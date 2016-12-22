package yuri.calculator

class Tokenizer {

  def tokenize(s: String): Seq[Token] = s.head match {
    case ' ' => tokenize(s.tail)
    case '+' => PlusToken +: tokenize(s.tail)
    case '-' => MinusToken +: tokenize(s.tail)
    case '*' => MultiplyToken +: tokenize(s.tail)
    case '/' => DivideToken +: tokenize(s.tail)
    case '^' => PowerToken +: tokenize(s.tail)
    case '(' => OpeningParenthesisToken +: tokenize(s.tail)
    case ')' => ClosingParenthesisToken +: tokenize(s.tail)
    case _ => {
      val pattern = """(-?\d+(\.\d+)?)(.+)""".r
      val m = pattern.findAllIn(s)

      if(m.hasNext) { DoubleToken(m.group(1).toDouble) +: tokenize(m.group(3)) }
      else Seq()
    }
  }

}
