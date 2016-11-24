package codespace.ticktack.strategies

import codespace.ticktack.{Field, Label, Player, Rules}

class Recursive(label:Label, rules: Rules) extends BasePlayer(label,rules) {

  override def nextStep(f: Field): ((Int, Int), Player) =
  {
    f.get(1,1) match {
      case None => ((1,1),this)
      case Some(l) if (l==label)  =>
           checkNearest(1,1,f)
    }
  }

  def checkNearest(x:Int,y:Int,f:Field):((Int,Int),Player) =
  {
     var retval: Option[(Int,Int)] = None
     for(cx <- 0 to 2;
         cy <- 0 to 2 if (cx!=x) || (cy!=y) ) {
       f.get(cx,cy) match {
         case None =>
           retval = checkNextStep(cx,cy,f)
           if (retval.isDefined) {
             return (retval.get,this)
           }
         case Some(l1) =>
       }
     }
    ???

  }

  def checkNextStep(x:Int,y:Int,f:Field):Option[(Int,Int)]=
  {
    val f1 = f.put(x,y,label)
    ???
    //val antagonist = new Player(otherLabel,rules)
    //val antagon
  }

  override def tell(s: String): Player = ???
}


