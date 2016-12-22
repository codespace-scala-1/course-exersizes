package yuri.phonecode

class PhonecodeEncoder(revLookup: ReverseLookup)
{
    def retrieveValidEncodings(s: String): Seq[String] = {

      val lookup = revLookup.lookup

      def retrieveEncodings(s: String, allowSkip: Boolean): Seq[String] = {


        def retrieveSplitEncodings(i: Int): Seq[String] = {
          val left = s.substring(0, i)
          val right = s.substring(i)

          lookup.get(left) match {
            case Some(lft) => {
              val rightEncodings = retrieveEncodings(right, allowSkip = true)
              //lft.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))
              for{l <- lft
                  r <- rightEncodings} yield (l + " "+r).trim
            }
            case _ => if (left.tail.isEmpty && allowSkip)
                            retrieveEncodings(right, allowSkip = false).map(r => (left + " " + r).trim)
            else Seq()
          }

        }

        if (s.isEmpty) Seq("")
        else {
          val (digitPart, letterPart) = (1 to s.length)
            .flatMap(retrieveSplitEncodings)
            .partition(_.matches("^[0-9] .+"))

          if (letterPart.isEmpty) digitPart
          else letterPart
        }
      }

      retrieveEncodings(s.replaceAll("[/-]", ""), true)
    }
}
