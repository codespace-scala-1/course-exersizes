import java.util.concurrent.{Executors, ScheduledThreadPoolExecutor, TimeUnit, TimeoutException}

import scala.concurrent.{Await, Future, Promise}
import scala.concurrent.duration._
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

val future = Future{
  Thread.sleep( Random.nextInt(1)*1000 )
  10
}

val p = Promise[Int]()

future.foreach( x => p trySuccess(x) )

val javaScheduler = Executors.newScheduledThreadPool(1)
javaScheduler.schedule(
  new Runnable {
    override def run() = {
      p tryFailure new TimeoutException()
    }
  },
  1, TimeUnit.SECONDS
)



p.future