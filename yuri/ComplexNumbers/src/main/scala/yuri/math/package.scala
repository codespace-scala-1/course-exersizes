package yuri

package object math {
  implicit def double2Complex(d: Double) : Complex = Complex(d, 0)

  implicit def int2Complex(i: Int) : Complex = Complex(i, 0)
}
