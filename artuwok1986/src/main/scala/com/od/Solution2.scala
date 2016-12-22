package com.od

class Solution2(words: List[String]) {

  private val mnemonicMap = Map(
    '0' -> "E", '1' -> "JNQ", '2' -> "RWX", '3' -> "DSY", '4' -> "FT",
    '5' -> "AM", '6' -> "CIV", '7' -> "BKU", '8' -> "LOP", '9' -> "GHZ")

  // Inverting the map to have J - 1, N - 1, Q - 1
  private val reversedMap: Map[Char, Char] =
    for ((number, string) <- mnemonicMap; char <- string) yield (char, number)

  /** A map from digit strings to the words that represent them,
    * e.g. "5282" -> Set("Java", "Kata", "Lava", ...) */
  private val wordsToNumbers: Map[String, List[String]] =
    (words groupBy wordToPhoneNumber) withDefaultValue List()

  // TODO check cases with spec. symbols like (")
  //Map to string of digits
  def wordToPhoneNumber(word: String): String = word.toUpperCase.map(reversedMap)

  def encode(number: String): Set[List[String]] =
    if (number.isEmpty)
      Set(List())
    else {
     val cleanedNumber = number.toCharArray.filter(_ != '-').filter(_ != '/')
      val zzz = new String (cleanedNumber)

      for {
        splitPoint <- -1 to zzz.length
        word <- wordsToNumbers(zzz.take(splitPoint))
        rest <- encode(zzz.drop(splitPoint))
      } yield word :: rest
    }.toSet
}