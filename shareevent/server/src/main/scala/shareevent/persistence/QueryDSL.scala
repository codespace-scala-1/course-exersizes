package shareevent.persistence


import scala.language.implicitConversions
import javax.naming.LimitExceededException

import scala.reflect.api.TypeTags
import shapeless.Poly

object QueryDSL {

  class ObjectMeta[T,This<:ObjectMeta[T,This]](val metaTableName:String)
  {
    def select = new TableSelectionExpression[This]()

    def field[F](name:String, isId:Boolean=false, optional:Boolean=false) =
      new ObjectFieldMeta[T,F,This](isId,name,optional,this)

  }

  class ObjectFieldMeta[O,T,OM <: ObjectMeta[O,OM]](
                                 val isId: Boolean,
                                 val name: String,
                                 val optional: Boolean,
                                 val table: ObjectMeta[O,OM]
                               ) extends FieldExpersion[T]
  {

    def === (other: FieldExpersion[T]) = Equals(this,other)
    def =*= (other: FieldExpersion[T]) = Equals(this,other)

    def !== (other: FieldExpersion[T]) = NonEquals(this,other)
    def isNil = IsNil(this)

    def <= (other: FieldExpersion[T])(implicit o: Ordering[T]) = LessEq(this,other)
    def >= (other: FieldExpersion[T])(implicit o: Ordering[T]) = GreaterEq(this,other)

    override def toString = s"field(${name})"
  }


  sealed trait QueryExpression[T]

  trait LimitOffset[T]
  {
    this: QueryExpression[T] =>

    def limit(count: Int) = new LimitExpression(this,count)
    def offset(count:Int) = new OffsetExpression(this,count)

  }


  class TableSelectionExpression[T] extends QueryExpression[T]
  {
    def where(expr:BooleanExpression) = new WhereExpression(this, expr)
  }

  case class WhereExpression[T](origin:TableSelectionExpression[T], expr: BooleanExpression) extends QueryExpression[T]

  case class LimitExpression[T](origin:QueryExpression[T],lim:Int) extends QueryExpression[T]
  case class OffsetExpression[T](origin:QueryExpression[T],lim:Int) extends QueryExpression[T]


  sealed trait FieldExpersion[T]

  case class Constant[T](val t:T) extends FieldExpersion[T]

  implicit def stringToConstant(s:String): Constant[String] = Constant(s)
  implicit def intToConstant(v:Int):Constant[Int] = Constant(v)

  implicit def enumerationToConstant[E <: scala.Enumeration](x: E#Value) = Constant(x)

  implicit def optionToConstant[T](v:Option[T])(implicit ct:Constant[T]):Constant[Option[T]] = Constant(v)

  sealed trait BooleanExpression
  {
    def || (other: BooleanExpression) = new OrBooleanExpression(this,other)
    def && (other: BooleanExpression) = new AndBooleanExpression(this, other)
  }

  sealed trait FieldComparison extends BooleanExpression

  case class Equals[T](x:FieldExpersion[T],y:FieldExpersion[T]) extends FieldComparison

  case class NonEquals[T](x:FieldExpersion[T],y:FieldExpersion[T]) extends FieldComparison

  case class LessEq[T](x:FieldExpersion[T],y:FieldExpersion[T])(implicit o:Ordering[T]) extends FieldComparison

  case class GreaterEq[T](x:FieldExpersion[T],y:FieldExpersion[T])(implicit o:Ordering[T]) extends FieldComparison


  case class IsNil[T](x:FieldExpersion[T]) extends BooleanExpression

  sealed trait BooleanOperator extends BooleanExpression

  sealed trait BooleanUnaryOperator extends BooleanOperator

  case class NotBooleanExpression(x:BooleanExpression) extends BooleanUnaryOperator

  def not(x:BooleanExpression) = NotBooleanExpression(x)

  sealed trait BooleanBinaryOperator extends BooleanOperator

  case class OrBooleanExpression(x: BooleanExpression, y:BooleanExpression) extends BooleanBinaryOperator

  case class AndBooleanExpression(x: BooleanExpression, y:BooleanExpression) extends BooleanBinaryOperator



}
