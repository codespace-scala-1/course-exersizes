package yuri.calculator

sealed trait Token

case class DoubleToken(x:Double) extends Token

case object PlusToken extends Token

case object MinusToken extends Token

case object MultiplyToken extends Token

case object DivideToken extends Token

case object PowerToken extends Token

case object OpeningParenthesisToken extends Token

case object ClosingParenthesisToken extends Token