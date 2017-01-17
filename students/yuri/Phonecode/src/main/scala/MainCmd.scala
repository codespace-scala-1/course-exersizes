import yuri.phonecode.{PhonecodeEncoder, ReverseLookup}

object MainCmd {

  def main(args: Array[String]): Unit = {

    import scala.io.Source

    val sourceT = Source.fromFile("data/test.t")
    val lookup = new ReverseLookup("data/test.w")
    val encoder = new PhonecodeEncoder(lookup)


    for (testLine <- sourceT.getLines()) {

      val encodings = encoder.retriveValidEncodings(testLine)

      for (enc <- encodings)
        println(s"$testLine: $enc")
    }
  }

}
