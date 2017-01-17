package yuri.conway.game

sealed trait CellState

case object Alive extends CellState
{
  override def toString = "@"
}

case object Dead extends CellState
{
  override def toString = "."
}