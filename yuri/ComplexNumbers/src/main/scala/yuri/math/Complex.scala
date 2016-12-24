package yuri.math

case class ComplexNumber[T](re : T, im : T) extends Fractional[ComplexNumber[T]]
{
  private val epsilon = 1e-14

  lazy val lengthSq = im*im + re*re
  lazy val length = math.sqrt(lengthSq)

  def +(other: ComplexNumber[T]): ComplexNumber[T] = ComplexNumber(this.re + other.re, this.im + other.im)

  def -(other: ComplexNumber[T]): ComplexNumber[T] = ComplexNumber(this.re - other.re, this.im - other.im)

  def unary_- : ComplexNumber[T] = ComplexNumber(-this.re, -this.im)

  def unary_+ = this

  def conj() = ComplexNumber[T](this.re, -this.im)

  def unary_~ = conj()

  def *(other: ComplexNumber[T]) =
    ComplexNumber[T](this.re*other.re - this.im*other.im, this.re*other.im + this.im*other.re)

  def /(d: Double): ComplexNumber[T] = {
    require(math.abs(d) > epsilon)
    ComplexNumber(this.re / d, this.im / d)
  }

  def /(other: ComplexNumber[T]): ComplexNumber[T] =
  {
    require(other.length > epsilon)
    this * (~other / other.lengthSq)
  }

  def reciprocal(): ComplexNumber[T] = {
    require(length > epsilon)
    ~this / lengthSq
  }

  override def toString = {

    if(re == 0){
      if(im > 0) s"$im*i"
      else if(im < 0) s"- ${-im}*i"
      else "0"
    }
    else{
      if(im > 0) s"$re + $im*i"
      else if(im < 0) s"$re - ${-im}*i"
      else re.toString
    }
  }

  override def equals(o: Any) = o match {
    case other: ComplexNumber[T] => (this - other).lengthSq < 1e-14
    case _ => false
  }

  override def plus(x: ComplexNumber[T], y: ComplexNumber[T]): ComplexNumber[T] = x + y

  override def minus(x: ComplexNumber[T], y: ComplexNumber[T]): ComplexNumber[T] = x - y

  override def times(x: ComplexNumber[T], y: ComplexNumber[T]): ComplexNumber[T] = x * y

  override def negate(x: ComplexNumber[T]): ComplexNumber[T] = -x

  override def fromInt(x: Int): ComplexNumber[T] = ???

  override def toInt(x: ComplexNumber[T]): Int = ???

  override def toLong(x: ComplexNumber[T]): Long = ???

  override def toFloat(x: ComplexNumber[T]): Float = {
    require(math.abs(x.im) < epsilon)
    x.re.toFloat
  }

  override def toDouble(x: ComplexNumber[T]): Double = {
    require(math.abs(x.im) < epsilon)
    x.re.toDouble
  }

  override def compare(x: ComplexNumber[T], y: ComplexNumber[T]): Int = ???

  override def div(x: ComplexNumber[T], y: ComplexNumber[T]): ComplexNumber[T] = x / y
}

//case class Complex(re : Double, im : Double) extends ComplexNumber

//case object ComplexZero extends ComplexNumber {
//  override def re: Double = 0.0
//  override def im: Double = 0.0
//}