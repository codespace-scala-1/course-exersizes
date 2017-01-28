package shareevent.simplemodel

import shapeless.{Poly, Poly2}
import shapeless.PolyDefns.Case

object FieldToEqComparison extends Poly2
{

  implicit val longCase:Case.Aux[Long,Long,Boolean]=
    at( (x,y)=> (x==y) )

  implicit val stringCase:Case.Aux[String,String,Boolean]=
    at( (x,y)=> (x==y) )

  implicit val booleanCase:Case.Aux[Boolean, Boolean,Boolean]=
    at( (x,y)=> (x==y))

  implicit def optionCase[A](implicit base:Case.Aux[A,A,Boolean]):Case.Aux[Option[A],A,Boolean] =
    at( (x,y) => x.exists( _ == y ) )

}