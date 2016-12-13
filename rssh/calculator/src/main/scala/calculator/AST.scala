package calculator


sealed trait AST

case class DoubleLiteral(x:Double) extends AST

case class Plus(x:AST, y:AST) extends AST

case class Minus(x:AST, y:AST) extends AST

case class Pow(x:AST, y:AST) extends AST



