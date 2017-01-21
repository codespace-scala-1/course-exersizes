package shareevent

import scala.util.Try

package object model {

  type Tagged[U] = { type Tag = U }
  type @@[T, U] = T with Tagged[U]

  def toTagged[T,U](v:T): T @@ U =
    v.asInstanceOf[T @@ U]

  implicit class optToTry[T<:PersistenceId[_,_]](val v: Option[T]) extends AnyVal
  {
    def toTry: Try[T] = v.toRight(new IllegalStateException("Object id not set")).toTry
  }

}
