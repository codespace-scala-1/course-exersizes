package codespace.example

import scala.async.Async._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object AsyncExample {


  def main(args:Array[String]): Unit = {

    val f = Future{
      Thread.sleep(10)
      100
    }

    val f1 = async {

      Console.println("before f")
      await(f)
      Console.println("after f")

      try {
        await(f)
        throw new RuntimeException("BE-be-be")
      }finally{
        Console.println("catched exeception")
      }

    }
    Console.println("f1 receieve")

    Await.ready(f1, 1 second)
    Console.println("f1 ready")


  }


}
