package codespace.ticktack

//TODO: pass to github
trait Game {

  val rules: Rules

  case class State(field:Field,a:Player,b:Player)
  {

    def endOfGame: Boolean = rules.isWin(field) != None

    def step():State =
    {
      if (endOfGame)
        this
      else {
        val (ij,nextA) = a.nextStep(field)
        if (rules.isCorrect(ij,field,a.label)) {
          val nextField = field.put(ij._1, ij._2, a.label)
          State(nextField, b, nextA)
        }else{
          State(field,nextA.tell("Bad step"),b)
        }
      }
    }



  }

  def play(a:Player,b:Player):Field = {
     var s = State(rules.emptyField,a,b)
     while(!s.endOfGame) {
       s = s.step()
     }
     s.field
  }


}
