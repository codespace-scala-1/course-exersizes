

class CatObj

trait CatArrow[X<:CatObj,Y<:CatObj]
{
  def x: X
  def y: Y
  //def apply()

  def |>[Z <: CatObj] (g:CatArrow[Y,Z]):CatArrow[X,Z]
}

