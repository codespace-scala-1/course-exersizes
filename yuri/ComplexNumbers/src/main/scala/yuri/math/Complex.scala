package yuri.math

abstract class ComplexNumber
{
  def re: Double
  def im: Double

  def +(other: Complex) = Complex(this.re + other.re, this.im + other.im)

  def -(other: ComplexNumber) = Complex(this.re - other.re, this.im - other.im)

  def unary_- = Complex(-this.re, -this.im)

  def unary_+ = this

  def conj() = Complex(this.re, -this.im)

  def unary_~ = conj()

  def lengthSq() = im*im + re*re

  def length() = math.sqrt(lengthSq)

  def *(other: Complex) =
    Complex(this.re*other.re - this.im*other.im, this.re*other.im + this.im*other.re)

  def /(d: Double):Option[Complex] =
    if(d > 0) Some(Complex(this.re / d, this.im / d))
    else None

  def /(other: ComplexNumber): Option[Complex] =
  {
    other match {
      case ComplexZero => None
      case _ => Some(this * (~other / other.lengthSq).get)
    }
  }

  def reciprocal(): Option[Complex] = {
    this match {
      case ComplexZero => None
      case _ => ~this / lengthSq
    }
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
    case other: ComplexNumber => (this - other).lengthSq < 1e-14
    case _ => false
  }
}

case class Complex(re : Double, im : Double) extends ComplexNumber

case object ComplexZero extends ComplexNumber {
  override def re: Double = 0.0
  override def im: Double = 0.0
}