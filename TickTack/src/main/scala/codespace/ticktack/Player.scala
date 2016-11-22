package codespace.ticktack

trait Player {

  val label: Label
  val rules: Rules

  def nextStep(f:Field):((Int,Int),Player)

  def tell(s:String):Player

}
