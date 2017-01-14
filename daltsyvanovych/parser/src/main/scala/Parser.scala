import scala.calculator.{Expr, Literal}

class Parser {

  def parse(s: String): Expr = {
    Literal(12)
  }

}

object Parser {

  implicit def transform(s: String): Expr = new Parser().parse(s)

}
