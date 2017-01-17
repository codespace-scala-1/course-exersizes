package codespace.ticktack.strategies

import codespace.ticktack.{Field, Label, Player, Rules}

class MachinePlayer(label:Label, rules:Rules) extends BasePlayer(label, rules) {

  override def nextStep(f: Field): Either[String, ((Int, Int), Player)]  = {
    var bestStep = (-1, -1)
    var bestScore = -1
    var win:Boolean = false

    def isCorner(i:Int, j:Int) = (i != 1) && ((i+j)%2 == 0)

    def isCenter(i:Int, j:Int) = (i == j) && (i == 1)

    val blockingScore = 1
    val ownAdvanceScore = 2

    def getScore(ij1:(Int, Int), ij2:(Int, Int)) = {

      val c1: Option[Label] = f.get(ij1._1, ij1._2)
      val c2: Option[Label] = f.get(ij2._1, ij2._2)

      if(c1.isEmpty) {
        if(c2.isEmpty) 2*ownAdvanceScore
        else if(c2.get == label) 4*ownAdvanceScore
        else 0
      }
      else if(c1.get == label){
        if(c2.isEmpty) 4*ownAdvanceScore
        else if(c2.get == label) 1000 //win
        else 0
      }
      else {
        if(!c2.isEmpty && (c2.get != label)) 1000 // block other player win
        else 0
      }
    }

    def getHorizontalScore(rowIndex: Int, colIndex: Int): Int = {
      getScore((rowIndex, (colIndex + 1)%3), (rowIndex, (colIndex + 2)%3))
    }

    def getVerticalScore(rowIndex: Int, colIndex: Int): Int = {
      getScore(((rowIndex + 1)%3, colIndex), ((rowIndex + 2)%3, colIndex))
    }

    def getMainDiagonalScore(rowIndex: Int): Int = {
      val index1 = (rowIndex + 1)%3;
      val index2 = (rowIndex + 2)%3;
      getScore((index1, index1), (index2, index2))
    }

    def getSecondaryDiagonalScore(rowIndex: Int): Int = {
      val index1 = (rowIndex+ 1)%3;
      val index2 = (rowIndex+ 2)%3;
      getScore((index1, 2 - index1), (index2, 2 - index2))
    }

    for(i <- 0 until 3 if !win) {

      val mainDiagonalScore = getMainDiagonalScore(i)
      val secondaryDiagonalScore = getSecondaryDiagonalScore(i)

      for(j <- 0 until 3 if !win) {

        if(f.get(i, j).isEmpty) {

          var score = getHorizontalScore(i, j) + getVerticalScore(i, j)

          if(i == j) score += mainDiagonalScore
          if(i == 2-j) score += secondaryDiagonalScore

          if (isCorner(i, j)) score += 3 * blockingScore
          else if (isCenter(i, j)) score += 4 * blockingScore
          else score += 2 * blockingScore

          if(score > bestScore ) {
            bestStep = (i,j)
            bestScore = score
          }

          win = (score >= 1000)
        }
      }
    }

    Right((bestStep, this))
  }

  override def tell(s: String): Player = {
    println(s)
    this
  }
}