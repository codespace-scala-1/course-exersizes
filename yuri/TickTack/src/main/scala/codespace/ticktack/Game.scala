package codespace.ticktack

trait Game {

  val rules: Rules

  case class State(field:Field,a:Player,b:Player)
  {
    def isWin: Boolean = rules.isWin(field) != None

    def endOfGame: Boolean = isWin|| rules.isDraw(field)

    def step(): Either[String,State] =
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

  def play(a:Player,b:Player): (Field, Boolean, Label) = {
     var s = State(rules.emptyField, a, b)

     while(!s.endOfGame) {
       println()
       println(s"Turn of ${s.a.label}:")

       s = s.step() match {
         case Left(message) => s.a.tell(message)
                                s
         case Right(s1) => s1
       }

       s.field.dump()
     }

    (s.field, s.isWin, s.b.label)
  }
}
