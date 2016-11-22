package codespace.ticktack

class ThreeRules extends Rules {

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

    override def put(i: Int, j: Int, l: Label): Field =
    {
      checkCorrect(i,j)
      get(i,j) match {
        case Some(label) =>
          throw new IllegalArgumentException("label already filled")
        case None   =>
          val nextRow: IndexedSeq[Option[Label]] = data(i).patch(j,Seq(Some(l)),j)
          val nextData: IndexedSeq[IndexedSeq[Option[Label]]] = data.patch(i,Seq(nextRow),i)
          ThreeField(nextData)
      }
    }

    def checkCorrect(i:Int,j:Int):Unit =
    {
      require(i >= 0)
      require(i < data.length)
      require(j >= 0)
      require(j < data(0).length)
    }

  }

  override def isCorrect(ij: (Int, Int), f: Field, l: Label): Boolean = ???

  override def isWin(f: Field): Option[Label] =
  {
    ???
  }

  override def emptyField: Field = new ThreeField(3)
}
