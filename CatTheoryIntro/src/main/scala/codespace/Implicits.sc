import scala.languageFeature.implicitConversions

//x.sqrt

// E x: p(x)
// A x: ~p(x) = ~ E x: p(x)
// forall(p) = not exists (not p)
// exists p = not forall (not p)


case class Point(x:Int, y:Int)


object Point
{


  trait PointAsNumeric extends Numeric[Point] {
    override def plus(x: Point, y: Point): Point =
      Point(x.x + y.x,x.y+y.y)

    override def minus(x: Point, y: Point): Point = ???

    override def times(x: Point, y: Point): Point = ???

    override def negate(x: Point): Point = ???

    override def fromInt(x: Int): Point = ???

    override def toInt(x: Point): Int = ???

    override def toLong(x: Point): Long = ???

    override def toFloat(x: Point): Float = ???

    override def toDouble(x: Point): Double = ???

    override def compare(x: Point, y: Point): Int = ???
  }


}


object Sqrt
{

  def apply[T](x:T)(implicit n:Numeric[T]):Double =
  {
    def v(s:Double,xn:Double) =
      (xn+s/xn)/2

    var done = false
    var xp = 1.0
    val s = n.toDouble(x)
    while(!done) {
      val xc = v(s,xp)
      done = Math.abs(xc-xp) < 0.001
      xp = xc
    }
    xp
  }


}

Sqrt(200)

implicit class WithSqrt[X](x:X)(implicit n:Numeric[X])
{

  def sqrt():Double = Sqrt(x)

  def ** (y:X):X = n.minus(x,y)

}


val x = 100

x ** 99

x.sqrt()
