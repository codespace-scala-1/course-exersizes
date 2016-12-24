package codespace.example

import scala.concurrent.Future

object VotingExample {


  def vote(node:String):Future[Boolean] = ???

  def ifVote[T](nodes:List[String],ifTrue: =>T,ifFalse: =>T):T = ???


}
