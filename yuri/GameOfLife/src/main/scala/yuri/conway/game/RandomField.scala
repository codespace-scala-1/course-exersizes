package yuri.conway.game

class RandomField(nx:Int, ny:Int) extends LifeField(RandomField.getInitialField(nx, ny))

object RandomField
{
  def getInitialField(nx:Int, ny:Int): Array[Array[CellState]] = {

    val r = scala.util.Random

    val data: Array[Array[CellState]] =
      Array.tabulate[CellState](ny, nx) { (i, j) => if (r.nextDouble() > 0.5) Alive else Dead }

    data
  }
}
