package codespace.example

import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global


object VotingExample {


  def vote(node:String):Future[Boolean] =
  {
    Future{
      Thread.sleep(10)
      (math.random() > 0.5)
    }
  }


  def ifVote[T](nodes:List[String],ifTrue: =>T,ifFalse: =>T):Future[T] =
  {
    val listVoteFutures: List[Future[Boolean]] = nodes.map(vote(_))
    val votesFuture:Future[List[Boolean]] = Future.sequence(nodes.map(vote(_)))
    for( votes <- votesFuture) yield {
      val nPros = votes.filter(x => x).length
      if (nPros >= (votes.length /2)) ifTrue else ifFalse
    }
  }

  def ifVoteLimited[T](nodes:List[String],ifTrue: =>T,ifFalse: =>T):Future[T] =
  {
    val listVoteFutures: List[Future[Boolean]] = nodes.map(vote(_))
    afterOneSecond().map{ _ =>
       ???
    }
  }

  val scheduledThreadPool = Executors.newScheduledThreadPool(1)

  def afterOneSecond(): Future[Unit] =
  {
    val result = Promise[Unit]
    scheduledThreadPool.schedule(
      new Runnable {
        override def run() = {
          result success ()
        }
      },
      1,
      TimeUnit.SECONDS)
    result.future
  }


  def ifVote3[T](node1:String,node2:String,node3:String,ifTrue: =>T,ifFalse: =>T):Future[T] =
  {
    ???
    val p = Promise[Int]()
  }


  def howToUser(): Unit =
  {
    var r = 0
    ifVote(List("A","B","C"), { r=1; "YES"} , { r=2; "NO" } )
  }


}
