package codespace.example

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object VotingExample {


  def vote(node:String):Future[Boolean] = ???

  def ifVote[T](nodes:List[String],ifTrue: =>T,ifFalse: =>T):Future[T] =
  {
    val votes = Future.sequence(nodes.map(vote(_)))
    ???
  }

  def ifVote3[T](node1:String,node2:String,node3:String,ifTrue: =>T,ifFalse: =>T):Future[T] =
  {
    ???
  }



}
