def mean(xs: IndexedSeq[Double]): Either[String,Double] =
if (xs.isEmpty)
  Left("mean of empty list!")
else
  Right(xs.sum / xs.length)


val two: IndexedSeq[Double] = IndexedSeq(1.2, 2.3)
val empty: IndexedSeq[Double] = IndexedSeq()

mean(two)

mean(empty)