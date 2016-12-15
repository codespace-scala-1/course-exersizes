package yuri.conway.game

trait Field {

  def get(i:Int, j:Int): Either[String, CellState]

  //def put(i:Int, j:Int, l:CellState): Either[String, Field]

  def randomSeed(): Field

  def glider(): Field

  def acorn(): Field

  def getAliveNeighbours(i:Int, j:Int): Int

  def next(): Either[String, Field]

  def dump(): Unit
}