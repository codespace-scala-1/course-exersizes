package codespace

import scala.util.Try

import CatIntro._

trait Request
{
  def email:String = ???
}

trait Operations[M[_] <: Functor[_]]
{

  def receiveRequest():M[Request] = ???

  def validate(r:Request):M[Request] = ???

  def canonizeEmail(s:String):M[String] = ???

}

object A {

  def process[M[_]](operations: Operations[M])(implicit mm: Monadic[M]): M[Int] = {

    import operations._


    for {
      request <- receiveRequest
      validatedRequest <- validate(request)
      email <- canonizeEmail(request.email)
    } yield {
      1
      //doSomething
    }

  }

}

  /*
  receiveRequest flatMap { request =>
  for{validateRequest <- validate(request)
  email <- canonizeEmail(request.email)
} yield 1
*/

