package codespace.ticktack

sealed trait Label

case object CrossLabel extends Label
case object ToeLabel extends Label
