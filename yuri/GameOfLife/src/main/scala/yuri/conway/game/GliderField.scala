package yuri.conway.game

class GliderField(nx:Int, ny:Int) extends LifeField(GliderField.getInitialField(nx, ny))

object GliderField
{
  def getInitialField(nx:Int, ny:Int): Array[Array[CellState]] = {
    val data: Array[Array[CellState]] = Array.fill[CellState](ny, nx) { Dead }

    data(3)(1) = Alive
    data(3)(2) = Alive
    data(3)(3) = Alive
    data(2)(3) = Alive
    data(1)(2) = Alive

    data
  }
}
