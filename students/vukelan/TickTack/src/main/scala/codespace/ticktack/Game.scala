package codespace.ticktack

//TODO: pass to github
trait Game {

  val rules: Rules

  case class State(field:Field,a:Player,b:Player)
  {

    def endOfGame: Boolean = rules.isWin(field).isDefined

    def step():Either[String,State] =
    {
      if (endOfGame)
        Left("Game Over")
      else {
        for { ijn <- a.nextStep(field)
              ((i, j), nextA) = ijn
             nextField <- field.put(i, j, a.label)
        } yield {
          State(nextField, b, nextA)
        }
      }
    }



  }

  def play(a:Player,b:Player):Field = {
     var s = State(rules.emptyField,a,b)
     while(!s.endOfGame) {
       s = s.step() match {
         case Left(message) => s.a.tell(message)
                               s
         case Right(s1) => s1
       }
     }
     s.field
  }


}
