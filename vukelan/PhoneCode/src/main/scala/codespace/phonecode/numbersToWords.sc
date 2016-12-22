def numToWor2(p1:String,p2:String=""):Unit = {
  p1.foreach(
    c => print(c)
  )
}

"small".map(x => x.toUpper)

numToWor2("123")

def numToWor(s:String):String = {
  if (s.length < 2) s
  else {
    s.map(
      char => (
        numToWor(s.slice(0,s.indexOf(char))),
        char,
        numToWor(s.slice(s.indexOf(char),s.length-1))
      )
    ).mkString("***\n")
  }
}

numToWor("12345")


def numbersToWords(s: String): IndexedSeq[Any] = {
  if (s.length < 2) s
  else {
    s.map(x =>
      (
        numbersToWords(s.slice(0, s.indexOf(x))),
        x,
        numbersToWords(s.slice(s.indexOf(x), s.length - 1))
      )
    )
  }
}

numbersToWords("string").mkString("\n\n")