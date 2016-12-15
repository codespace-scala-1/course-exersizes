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
           val nextRow: IndexedSeq[Option[Label]] = data(i).patch(j, Seq(Some(l)), j)
           val nextData: IndexedSeq[IndexedSeq[Option[Label]]] = data.patch(i, Seq(nextRow), i)
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
         if (f.get(1,col)==Some(l) && f.get(2,col)==Some(l))
            Some(l)
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
}
