package codespace.example

import scala.concurrent.Future
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

  def ifVote3[T](node1:String,node2:String,node3:String,ifTrue: =>T,ifFalse: =>T):Future[T] =
  {
    ???
  }

  def howToUser(): Unit =
  {

    ifVote(List("A","B","C"), "YES", "NO" )
  }


}
