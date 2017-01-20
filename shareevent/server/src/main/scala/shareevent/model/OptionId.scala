package shareevent.model

import scala.util.{Failure, Success, Try}


sealed trait OptionId[+K, +Tag]
{

  def tryId: Try[Id[K,Tag]]

}

case class Id[+K,+Tag](id:K) extends OptionId[K,Tag]
{

  override def tryId: Try[Id[K,Tag]] = Success(this)

}

case object NoId extends OptionId[Nothing,Nothing]
{

  override def tryId: Try[Id[Nothing,Nothing]] = Failure(new IllegalStateException("id is not set"))


}

