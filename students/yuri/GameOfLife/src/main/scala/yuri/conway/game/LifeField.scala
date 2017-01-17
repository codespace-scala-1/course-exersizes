package yuri.conway.game

import scala.util.Try

class LifeField(var data: Array[Array[CellState]]) {

  def ny:Int = data.length
  def nx:Int = data(0).length

  def get(i:Int, j:Int): Either[String, CellState] =
  {
    Try {
      checkCorrect(i, j)
      data(i)(j)
    }.toEither.left.map(_.getMessage)
  }

  def getAliveNeighbours(i:Int, j:Int): Int = {
    var count = 0

    for(x <- -1 to 1; y <- -1 to 1 if((x != 0) || (y != 0))){
      val ii = (ny + x + i) % ny
      val jj = (nx + y + j) % nx
      if(data(ii)(jj) == Alive) count += 1
    }

    count
  }

  def next(): Either[String, LifeField] = {

    val nextData: Array[Array[CellState]] =
        Array.tabulate[CellState](ny, nx) { (i,j) => data(i)(j) }

    for(i <- 0 until ny; j <- 0 until nx) {
      val n = getAliveNeighbours(i, j)

      data(i)(j) match {
        case Dead => if(n == 3) nextData(i)(j) = Alive
        case _ => if((n < 2) || (n > 3)) nextData(i)(j) = Dead
      }
    }

    Right(value = new LifeField(nextData))
  }

  def checkCorrect(i:Int, j:Int): Unit =
  {
    require(i >= 0)
    require(i < ny)
    require(j >= 0)
    require(j < nx)
  }

  def dump(): Unit = {

    for(i <- 0 until ny) {
      for(j <- 0 until nx) {
        print(s" ${data(i)(j)}")
      }
      println()
    }
  }
}
