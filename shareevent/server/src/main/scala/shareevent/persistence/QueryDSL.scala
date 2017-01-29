package shareevent.persistence


import scala.language.implicitConversions
import javax.naming.LimitExceededException

import scala.reflect.api.TypeTags
import shapeless.{LabelledGeneric, Poly}

object QueryDSL {

  abstract class ObjectMeta[T,This<:ObjectMeta[T,This]](val metaTableName:String)
  {
    type Self = This

    def select = new TableSelectionExpression[This]()

    def field[F](name:String, isId:Boolean=false, optional:Boolean=false) =
      new ObjectFieldMeta[T,F,This](isId,name,optional,this)

    def toMap(x:T):Map[Symbol,Any]

  }

  trait FieldType[T] {
    type FieldType = T
  }

  def select[T](implicit om:ObjectMeta[T,_]) = om.select


  class ObjectFieldMeta[O,T,OM <: ObjectMeta[O,OM]](
                                 val isId: Boolean,
                                 val name: String,
                                 val optional: Boolean,
                                 val table: ObjectMeta[O,OM]
                               ) extends FieldExpression[T]
  {

    type ObjectType = O
    type MetaType = OM


    def === (other: FieldExpression[T]) = Equals(this,other)
    def =*= (other: FieldExpression[T]) = Equals(this,other)

    def !== (other: FieldExpression[T]) = NonEquals(this,other)
    def isNil = IsNil(this)

    def < (other: FieldExpression[T])(implicit o: Ordering[T]) = Less(this,other,o)
    def <=(other: FieldExpression[T])(implicit o: Ordering[T]) = LessEq(this,other,o)
    def > (other: FieldExpression[T])(implicit o: Ordering[T]) = Greater(this,other,o)
    def >=(other: FieldExpression[T])(implicit o: Ordering[T]) = GreaterEq(this,other,o)

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


  sealed trait FieldExpression[T] extends FieldType[T]

  case class Constant[T](val t:T) extends FieldExpression[T]

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
  {
    type FieldType
    def x:FieldExpression[FieldType]
    def y:FieldExpression[FieldType]
  }

  object FieldComparison
  {
    type Aux[T] = FieldComparison with FieldType[T]
  }



  case class Equals[T](x:FieldExpression[T], y:FieldExpression[T]) extends FieldComparison with FieldType[T]

  case class NonEquals[T](x:FieldExpression[T], y:FieldExpression[T]) extends FieldComparison with FieldType[T]

  case class Less[T](x:FieldExpression[T], y:FieldExpression[T], o:Ordering[T]) extends FieldComparison with FieldType[T]

  case class LessEq[T](x:FieldExpression[T], y:FieldExpression[T], o:Ordering[T]) extends FieldComparison with FieldType[T]

  case class Greater[T](x:FieldExpression[T], y: FieldExpression[T], o:Ordering[T]) extends FieldComparison with FieldType[T]

  case class GreaterEq[T](x:FieldExpression[T], y:FieldExpression[T], o:Ordering[T]) extends FieldComparison with FieldType[T]


  case class IsNil[T](x:FieldExpression[T]) extends BooleanExpression

  sealed trait BooleanOperator extends BooleanExpression

  sealed trait BooleanUnaryOperator extends BooleanOperator

  case class NotBooleanExpression(x:BooleanExpression) extends BooleanUnaryOperator

  def not(x:BooleanExpression) = NotBooleanExpression(x)

  sealed trait BooleanBinaryOperator extends BooleanOperator

  case class OrBooleanExpression(x: BooleanExpression, y:BooleanExpression) extends BooleanBinaryOperator

  case class AndBooleanExpression(x: BooleanExpression, y:BooleanExpression) extends BooleanBinaryOperator



}
