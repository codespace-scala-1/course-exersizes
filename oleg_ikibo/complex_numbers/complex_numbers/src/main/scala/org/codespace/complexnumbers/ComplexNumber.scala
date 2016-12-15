package org.codespace.complexnumbers

sealed class ComplexNumber(val re: Int, val im: Int) {

  def unary_- : ComplexNumber = {
    new ComplexNumber(-re, -im)
  }

  def add(that : ComplexNumber) : ComplexNumber = {
    new ComplexNumber(re + that.re, im + that.im)
  }

  def +(that : ComplexNumber) : ComplexNumber = {
    this add that
  }

  def minus(that : ComplexNumber) : ComplexNumber = {
    this + -that
  }

  def -(that : ComplexNumber) : ComplexNumber = {
    this minus that
  }

  override def equals(that: scala.Any): Boolean = {
    that match {
      case that: ComplexNumber => that.re == re && that.im == im
      case _ =>
    }
  }

  // TODO : implement hashCode conforming with equals

  override def toString : String = {
    if (im > 0) re + " + i*" + im
    else re + " - i*" + -im
  }
}
