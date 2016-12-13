package codespace

import scala.util.Try

import CatIntro._

trait Request
{
  def email:String = ???
}

trait Operations
{

  def receiveRequest():Option[Request] = ???

  def validate(r:Request):Option[Request] = ???

  def canonizeEmail(s:String):Option[String] = ???

}

object A {

  def process(operations: Operations): Option[Int] = {

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

