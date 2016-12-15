package codespace.ticktack

trait Player {

  val label: Label
  val rules: Rules

  def nextStep(f:Field):Either[String,((Int,Int),Player)]

  def tell(s:String):Player

}
