package codespace.ticktack

sealed trait Label

case object CrossLabel extends Label {
  override def toString = "x"
}

case object ToeLabel extends Label {
  override def toString = "0"
}
