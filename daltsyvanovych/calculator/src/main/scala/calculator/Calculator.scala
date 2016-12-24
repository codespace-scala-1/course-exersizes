package scala.calculator

sealed abstract class Expr

final case class Literal(v: Double) extends Expr

final case class Plus(a: Expr, b: Expr) extends Expr

final case class Minus(a: Expr, b: Expr) extends Expr

final case class Times(a: Expr, b: Expr) extends Expr

final case class Divide(a: Expr, b: Expr) extends Expr

final case class Pow(a: Expr, b: Int) extends Expr

final case class Not(a: Expr) extends Expr

final case class Sqrt(a: Expr) extends Expr



object Calculator {

  def eval(expr: Expr): Double = expr match {
    case Literal(v) => v
    case Plus(a, b) => eval(a) + eval(b)
    case Minus(a, b) => eval(a) - eval(b)
    case Times(a, b) => eval(a) * eval(b)
    case Divide(a, b) => eval(a) / eval(b)
    case Not(a) => -eval(a)
    case Pow(a, b) => Math.pow(eval(a), b)
    case Sqrt(a) => Math.sqrt(eval(a))
  }

}
