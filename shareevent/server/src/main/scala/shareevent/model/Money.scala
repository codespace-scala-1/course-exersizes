package shareevent.model

class Money(val value:BigDecimal) extends AnyVal



object Money
{

  def apply(value: BigDecimal) = new Money(value)

  def apply(value: Long) = new Money(BigDecimal.valueOf(value))

  def unapply(arg: Money): Option[BigDecimal] = Some(arg.value)

  final val zero: Money = new Money(BigDecimal.valueOf(0L))

}