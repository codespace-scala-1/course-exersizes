package yuri.phonecode

class PhonecodeEncoder(revLookup: ReverseLookup)
{
<<<<<<< HEAD
    def retriveValidEncodings(s: String): Seq[String] = {
=======
    def retrieveValidEncodings(s: String): Seq[String] = {
>>>>>>> cb02a6f9d89c744ec20a5fb502775054adea7243

      val lookup = revLookup.lookup

<<<<<<< HEAD
      def retriveEncodings(s: String, allowSkip: Boolean): Seq[String] = {

        def retriveSplitEncodings(i: Int): Seq[String] = {
=======
      def retrieveEncodings(s: String, allowSkip: Boolean): Seq[String] = {


        def retrieveSplitEncodings(i: Int): Seq[String] = {
>>>>>>> cb02a6f9d89c744ec20a5fb502775054adea7243
          val left = s.substring(0, i)
          val right = s.substring(i)

          lookup.get(left) match {
            case Some(lft) => {
<<<<<<< HEAD
              val rightEncodings = retriveEncodings(right, allowSkip = true)
              lft.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))
            }

            case _ => if (left.tail.isEmpty && allowSkip) retriveEncodings(right, allowSkip = false).map(r => (left + " " + r).trim)
=======
              val rightEncodings = retrieveEncodings(right, allowSkip = true)
              //lft.flatMap(l => rightEncodings.map(r => (l + " " + r).trim))
              for{l <- lft
                  r <- rightEncodings} yield (l + " "+r).trim
            }
            case _ => if (left.tail.isEmpty && allowSkip)
                            retrieveEncodings(right, allowSkip = false).map(r => (left + " " + r).trim)
>>>>>>> cb02a6f9d89c744ec20a5fb502775054adea7243
            else Seq()
          }

        }

        if (s.isEmpty) Seq("")
        else {
          val (digitPart, letterPart) = (1 to s.length)
<<<<<<< HEAD
            .flatMap(retriveSplitEncodings)
=======
            .flatMap(retrieveSplitEncodings)
>>>>>>> cb02a6f9d89c744ec20a5fb502775054adea7243
            .partition(_.matches("^[0-9] .+"))

          if (letterPart.isEmpty) digitPart
          else letterPart
        }
      }

<<<<<<< HEAD
      retriveEncodings(s.replaceAll("[/-]", ""), true)
=======
      retrieveEncodings(s.replaceAll("[/-]", ""), true)
>>>>>>> cb02a6f9d89c744ec20a5fb502775054adea7243
    }
}
