package yuri.phonecode

class PhonecodeEncoder(revLookup: ReverseLookup)
{
    def getValidEncodings(s: String): Seq[String] = {

      val lookup = revLookup.lookup.get

      def getEncodings(s: String, allowSkip: Boolean): Seq[String] = {

        def getSplitEncodings(i: Int): Seq[String] = {
          val left = s.substring(0, i)
          val right = s.substring(i)

          lookup.get(left) match {
            case Some(lft) => {
              val rightEncodings = getEncodings(right, true)
              lft.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))
            }

            case _ => if (left.tail.isEmpty && allowSkip) getEncodings(right, false).map(r => (left + " " + r).trim)
            else Seq()
          }
        }

        if (s.isEmpty) Seq("")
        else {
          val (digitPart, letterPart) = (1 to s.length)
            .flatMap(getSplitEncodings)
            .partition(_.matches("^[0-9] .+"))

          if (letterPart.isEmpty) digitPart
          else letterPart
        }
      }

      getEncodings(s.replaceAll("[/-]", ""), true)
    }
}
