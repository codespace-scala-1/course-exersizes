package yuri.calculator

class Parser {

  def parse(s: String): Either[String, AST] = {

    val rexpr="""((-?\d+)(\.\d+)?)(\s+([+*-])\s+(.+))?""".r

    val l = rexpr.findAllIn(s)

    if(!l.hasNext) {
      Left(s"Cannot parse expression '$s'")
    }
    else {

      val left = DoubleLiteral(l.group(1).toDouble)

      if(l.group(4) == null) {
        Right(left)
      }
      else{
        val right = parse(l.group(6))

        if(right.isLeft) Left(right.left.get)
        else Right(Plus(left, right.right.get))
      }
    }
  }

  def parseLevelOne(tokens: Seq[Token]): AST = {

    val plusMinusIdx = tokens.indexWhere(t => t == PlusToken || t == MinusToken)

    if(plusMinusIdx == -1){
      parseLevelTwo(tokens)
    }
    else{
      val (leftAst, rightAst) = (parseLevelOne(tokens.take(plusMinusIdx)), parseLevelOne(tokens.drop(plusMinusIdx + 1)))

      tokens(plusMinusIdx) match {
        case PlusToken => Plus(leftAst, rightAst)
        case MinusToken => Minus(leftAst, rightAst)
      }
    }
  }

  def parseLevelTwo(tokens: Seq[Token]): AST = {

    val multDivIdx = tokens.indexWhere(t => t == MultiplyToken || t == DivideToken)

    if(multDivIdx == -1){
      parseLevelThree(tokens).right.get
    }
    else{
      val (leftAst, rightAst) = (parseLevelOne(tokens.take(multDivIdx)), parseLevelOne(tokens.drop(multDivIdx + 1)))

      tokens(multDivIdx) match {
        case DivideToken => Div(leftAst, rightAst)
        case MultiplyToken => Product(leftAst, rightAst)
      }
    }
  }

    def parseLevelThree(tokens: Seq[Token]): Either[String, AST] = {

      if(tokens.length != 1) Left("One token is expected")

      tokens.head match {
        case DoubleToken(d) => Right(DoubleLiteral(d))
        case _ => Left("Terminal should be DoubleToken")
      }
    }
}
