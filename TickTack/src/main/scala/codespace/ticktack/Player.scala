package codespace.ticktack

trait Player {

  val label: Label

  def nextStep(f:Field):((Int,Int),Player)

  def tell(s:String):Player

}
