
import cats._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

package object shareevent {

  implicit object TryId extends (Try ~> Try) {

    override def apply[A](fa: Try[A]): Try[A] = fa

  }

  implicit object TryToFuture extends (Try ~> Future) {

    override def apply[A](fa: Try[A]): Future[A] =
      fa match {
        case Success(x) => Future successful x
        case Failure(ex) => Future failed ex
      }

  }

  class FutureMonad(implicit ec:ExecutionContext) extends Monad[Future]
  {

    override def flatMap[A, B](fa: Future[A])(f: (A) => Future[B]): Future[B] =
      fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: (A) => Future[Either[A, B]]): Future[B] = {
      f(a) flatMap {
        case Left(a) => tailRecM(a)(f)
        case Right(b) => Future successful(b)
      }
    }

    override def pure[A](x: A): Future[A] = Future successful x
  }

}