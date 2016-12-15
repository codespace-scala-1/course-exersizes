package codespace.ticktack

import scala.util.Try

object ThreeRules extends Rules {

   //data
   //   data(0) ***
   //   data(1) ***
   //   data(2) ***

  case class ThreeField(val data: IndexedSeq[IndexedSeq[Option[Label]]]) extends Field
  {

    def this(n:Int) =
    {
      this(data = (1 to n) map { _ => (1 to n) map (_ => None)  })
    }

    override def get(i:Int,j:Int):Option[Label]=
    {
      checkCorrect(i,j)
      data(i)(j)
    }

    override def put(i: Int, j: Int, l: Label): Either[String,Field] =
    {
     Try {
       checkCorrect(i, j)
       get(i, j) match {
         case Some(label) =>
           throw new IllegalArgumentException("label already filled")
         case None =>
           val nextRow: IndexedSeq[Option[Label]] = data(i).patch(j, Seq(Some(l)), 1)
           val nextData: IndexedSeq[IndexedSeq[Option[Label]]] = data.patch(i, Seq(nextRow), 1)
           ThreeField(nextData)
       }
     }.toEither.left.map(_.getMessage)
    }

    def checkCorrect(i:Int,j:Int):Unit =
    {
      require(i >= 0)
      require(i < data.length)
      require(j >= 0)
      require(j < data(0).length)
    }

    override def dump(): Unit = {

      for(i <- 0 until data.length) {
        for(j <- 0 until data(0).length) {
          data(i)(j) match {
            case Some(CrossLabel) => print(" X")
            case Some(ToeLabel) => print(" O")
            case _ => print(" _")
          }
        }
        println()
      }
    }
  }

  override def isCorrect(ij: (Int, Int), f: Field, l: Label): Boolean =
  {
    val (i,j) = ij
    i >= 0 && i < 3 && j >= 0 && j < 3 && f.get(i,j).isEmpty
  }

  override def isWin(f: Field): Option[Label] =
  {

    def horizontalWin(row:Int):Option[Label]={
      f.get(row,0) match {
        case Some(l) => if (f.get(row,1)==Some(l) && f.get(row,2)==Some(l)) {
                          Some(l)
                        } else None
        case None => None
      }
    }

    def verticalWin(col:Int):Option[Label] = {
      f.get(0,col) flatMap { l =>
         if (f.get(1,col)==Some(l) && f.get(2,col)==Some(l)) {
            Some(l)
         }
         else None
      }
    }

    def diagonalWin():Option[Label] = {

      def checkLeft(l:Label) = f.get(0,0) == f.get(2,2) && f.get(0,0)==Some(l)

      def checkRight(l:Label) = f.get(2,0) == f.get(0,2) && f.get(2,0)==Some(l)

      f.get(1,1) flatMap { l =>
         if (checkLeft(l)||checkRight(l))
            Some(l)
         else
           None
      }

    }

    (0 until 3).find( horizontalWin(_).isDefined  )match {
      case Some(row) => f.get(row,0)
      case None =>
        (0 until 3).find(verticalWin(_).isDefined) match {
          case Some(col) => f.get(0,col)
          case None => diagonalWin()
        }
    }

  }

  override def emptyField: Field = new ThreeField(3)

  override def isDraw(f: Field): Boolean = {

    def scanLabels(fnc: Int => Option[Label]): Boolean = {
      (0 to 2).map(fnc).filter(!_.isEmpty).distinct.length != 2
    }

    def scanRows(): Boolean = {

      var winIsPossible = false

      for(i <- 0 to 2 if !winIsPossible)
        winIsPossible = scanLabels(f.get(i, _))

      winIsPossible
    }

    def scanColumns(): Boolean = {

      var winIsPossible = false

      for(j <- 0 to 2 if !winIsPossible)
        winIsPossible = scanLabels(f.get(_, j))

      winIsPossible
    }

    def scanDiagonals(): Boolean = scanLabels(i => f.get(i, i)) || scanLabels(i => f.get(i, 2-i))

    !(scanRows() || scanColumns() || scanDiagonals())
  }
}
