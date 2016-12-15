package yuri.conway.game

import scala.util.{Random, Try}

object TenRules extends Rules {

  case class TwentyField(val data: Array[Array[CellState]]) extends Field
  {
    def this(n:Int) =
    {
      this(data = Array.fill[CellState](20, 50) { Dead })
    }

    override def randomSeed(): Field = {
      val r = Random

      val d: Array[Array[CellState]] = Array.tabulate[CellState](20, 50)
                                         { (i,j) => if(r.nextDouble() > 0.5) Alive else Dead }
      TwentyField(d)
    }

    override def glider(): Field = {

      val d: Array[Array[CellState]] = Array.fill[CellState](20, 50) { Dead }

      d(3)(1) = Alive
      d(3)(2) = Alive
      d(3)(3) = Alive
      d(2)(3) = Alive
      d(1)(2) = Alive

      TwentyField(d)
    }

    override def acorn(): Field = {

      val d: Array[Array[CellState]] = Array.fill[CellState](20, 50) { Dead }

      d(10)(11) = Alive
      d(11)(13) = Alive
      d(12)(10) = Alive
      d(12)(11) = Alive
      d(12)(14) = Alive
      d(12)(15) = Alive
      d(12)(16) = Alive

      TwentyField(d)
    }

    override def get(i:Int, j:Int): Either[String, CellState] =
    {
      Try {
        checkCorrect(i, j)
        data(i)(j)
      }.toEither.left.map(_.getMessage)
    }

    override def getAliveNeighbours(i:Int, j:Int): Int = {
      var count = 0

      for(x <- -1 to 1; y <- -1 to 1 if((x != 0) || (y != 0))){
        val ii = (20 + x + i) % 20
        val jj = (50 + y + j) % 50
        if(data(ii)(jj) == Alive) count += 1
      }

      count
    }

    def next(): Either[String, Field] = {

      val nextData: Array[Array[CellState]] = Array.tabulate[CellState](20, 50) { (i,j) => data(i)(j) }

      for(i <- 0 until 20; j <- 0 until 50) {
        val n = getAliveNeighbours(i, j)

        data(i)(j) match {
          case Dead => if(n == 3) nextData(i)(j) = Alive
          case _ => if((n < 2) || (n > 3)) nextData(i)(j) = Dead
        }
      }

      Right(TwentyField(nextData))
    }

    def checkCorrect(i:Int, j:Int): Unit =
    {
      require(i >= 0)
      require(i < data.length)
      require(j >= 0)
      require(j < data(0).length)
    }

    override def dump(): Unit = {

      for(i <- 0 until data.length) {
        for(j <- 0 until data(0).length) {
            print(s" ${data(i)(j)}")
        }
        println()
      }
    }
  }

  override def emptyField: Field = new TwentyField(20)

}