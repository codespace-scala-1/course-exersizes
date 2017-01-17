package org.codespace.complexnumbers

sealed class ComplexNumber(val re: Double, val im: Double) {

  lazy val mod = re * re + im * im

  def unary_- : ComplexNumber = {
    new ComplexNumber(-re, -im)
  }

  def add(that : ComplexNumber) : ComplexNumber = {
    new ComplexNumber(re + that.re, im + that.im)
  }

  def +(that : ComplexNumber) : ComplexNumber = {
    this add that
  }

  def subtract(that : ComplexNumber) : ComplexNumber = {
    this + -that
  }

  def -(that : ComplexNumber) : ComplexNumber = {
    this subtract that
  }

  def multiply(that : ComplexNumber) : ComplexNumber = {
    val newRe: Double = re * that.re - im * that.im
    val newIm: Double = re * that.im + im * that.re
    new ComplexNumber(newRe, newIm)
  }

  def *(that : ComplexNumber) : ComplexNumber = {
    this multiply that
  }

  def divide(that : ComplexNumber) : ComplexNumber = {
    val newRe : Double = (re * that.re + im * that.im)/that.mod
    val newIm : Double = (im * that.re - re * that.im)/that.mod
    new ComplexNumber(newRe, newIm)
  }

  def /(that : ComplexNumber) : ComplexNumber = {
    this divide that
  }

  override def equals(that: scala.Any): Boolean = {
    that match {
      case that: ComplexNumber => that.re == re && that.im == im
      case _ => false
    }
  }

  override def hashCode(): Int = 31 + re.hashCode() + im.hashCode()

  override def toString : String = {
    if (re == 0 && im == 0) 0.toString
    else if (im == 0) re.toString
    else if (re == 0 && im > 0) "i*" + im
    else if (re == 0 && im < 0) "- i*" + -im
    else if (im > 0) re + " + i*" + im
    else re + " - i*" + -im
  }
}
