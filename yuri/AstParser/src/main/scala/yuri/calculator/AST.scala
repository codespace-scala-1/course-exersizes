package yuri.calculator

sealed trait AST

case class DoubleLiteral(x:Double) extends AST

case class Plus(x:AST, y:AST) extends AST

case class Minus(x:AST, y:AST) extends AST

case class Product(x:AST, y:AST) extends AST

case class Div(x:AST, y:AST) extends AST

case class Pow(x:AST, y:AST) extends AST
