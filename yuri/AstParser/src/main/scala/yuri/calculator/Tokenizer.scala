package yuri.calculator

class Tokenizer {

  def tokenize(s: String): Seq[Token] = {

    if(s == null || s.isEmpty) Seq()

    val tail = if(s.length > 1) s.tail else null

    s.head match {
      case ' ' => tokenize(tail)
      case '+' => PlusToken +: tokenize(tail)
      case '-' => MinusToken +: tokenize(tail)
      case '*' => MultiplyToken +: tokenize(tail)
      case '/' => DivideToken +: tokenize(tail)
      case '^' => PowerToken +: tokenize(tail)
      case '(' => OpeningParenthesisToken +: tokenize(tail)
      case ')' => ClosingParenthesisToken +: tokenize(tail)
      case _ => {
        val pattern = """(-?\d+(\.\d+)?)(.+)?""".r
        val m = pattern.findAllIn(s)

        if (m.hasNext) {
          val rightPart = m.group(3)

          if (rightPart != null)
            DoubleToken(m.group(1).toDouble) +: tokenize(rightPart)
          else Seq(DoubleToken(m.group(1).toDouble))
        }
        else Seq()
      }
    }
  }

}
