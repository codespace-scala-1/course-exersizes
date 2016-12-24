package yuri.phonecode

class PhonecodeEncoder(revLookup: ReverseLookup)
{
    def retriveValidEncodings(s: String): Seq[String] = {

      val lookup = revLookup.lookup.get

      def retriveEncodings(s: String, allowSkip: Boolean): Seq[String] = {

        def retriveSplitEncodings(i: Int): Seq[String] = {
          val left = s.substring(0, i)
          val right = s.substring(i)

          lookup.get(left) match {
            case Some(lft) => {
              val rightEncodings = retriveEncodings(right, allowSkip = true)
              lft.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))
            }

            case _ => if (left.tail.isEmpty && allowSkip) retriveEncodings(right, allowSkip = false).map(r => (left + " " + r).trim)
            else Seq()
          }
        }

        if (s.isEmpty) Seq("")
        else {
          val (digitPart, letterPart) = (1 to s.length)
            .flatMap(retriveSplitEncodings)
            .partition(_.matches("^[0-9] .+"))

          if (letterPart.isEmpty) digitPart
          else letterPart
        }
      }

      retriveEncodings(s.replaceAll("[/-]", ""), true)
    }
}
