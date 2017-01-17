package yuri.conway.game

class AcornField(nx:Int, ny:Int) extends LifeField(AcornField.getInitialField(nx, ny))

object AcornField
{
  def getInitialField(nx:Int, ny:Int): Array[Array[CellState]] = {
    val data: Array[Array[CellState]] = Array.fill[CellState](ny, nx) { Dead }

    val yShift = ny/2 - 1
    val xShift = nx/2 - 3

    data(0 + yShift)(1 + xShift) = Alive
    data(1 + yShift)(3 + xShift) = Alive
    data(2 + yShift)(0 + xShift) = Alive
    data(2 + yShift)(1 + xShift) = Alive
    data(2 + yShift)(4 + xShift) = Alive
    data(2 + yShift)(5 + xShift) = Alive
    data(2 + yShift)(6 + xShift) = Alive

    data
  }
}
