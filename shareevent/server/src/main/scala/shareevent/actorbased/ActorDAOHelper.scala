package shareevent.actorbased

import akka.actor.ActorRef

trait ActorDAOHelper[K,T] {


  def findOrCreate(k:K, v:T):ActorRef



}
