package com.phonecode

import scala.collection.mutable

class Encoder {

  val zeroEncode = List('E', 'e')
  val oneEncode = List('J', 'N', 'Q', 'j', 'n', 'q')
  val twoEncode = List('R', 'W', 'X', 'r', 'w', 'x')
  val threeEncode = List('D', 'S', 'Y', 'd', 's', 'y')
  val fourEncode = List('F', 'T', 'f', 't')
  val fiveEncode = List('A', 'M', 'a', 'm')
  val sixEncode = List('C', 'I', 'V', 'c', 'i', 'v')
  val sevenEncode = List('B', 'K', 'U', 'b', 'k', 'u')
  val eightEncode = List('L', 'O', 'P', 'l', 'o', 'p')
  val nineEncode = List('G', 'H', 'Z', 'g', 'h', 'z')


  def phoneNumberToChars(numbersList: List[String]): mutable.Map[String, List[Char]] = {
    val resultMap = scala.collection.mutable.Map.empty[String, List[Char]]
    for (phoneNumber <- numbersList) {

      var resultListOfChars: List[Char] = List()
      val x = phoneNumber.toCharArray.filter(_ != '-').filter(_ != '/')
      for (z <- x) z match {
        case '0' => resultListOfChars = resultListOfChars ++ zeroEncode
        case '1' => resultListOfChars = resultListOfChars ++ oneEncode
        case '2' => resultListOfChars = resultListOfChars ++ twoEncode
        case '3' => resultListOfChars = resultListOfChars ++ threeEncode
        case '4' => resultListOfChars = resultListOfChars ++ fourEncode
        case '5' => resultListOfChars = resultListOfChars ++ fiveEncode
        case '6' => resultListOfChars = resultListOfChars ++ sixEncode
        case '7' => resultListOfChars = resultListOfChars ++ sevenEncode
        case '8' => resultListOfChars = resultListOfChars ++ eightEncode
        case '9' => resultListOfChars = resultListOfChars ++ nineEncode
        case _ => resultListOfChars
      }
      resultMap.put(new String(x), resultListOfChars.sorted)
    }
    resultMap
  }

  def mappingToWord(arrayToCheck: mutable.Map[String, List[Char]]) = {
    val finalWordsList: mutable.Map[String, List[String]] = mutable.Map()

    for (zx <- arrayToCheck) {
      val filteredDictionary = Dictionary.words.filter(x => x.length <= zx._1.length)
      for (word <- filteredDictionary) {
        val wordSet = word.toCharArray.toSet
        val arrayToCheckSet = zx._2.toSet
        val bool = wordSet.subsetOf(arrayToCheckSet)
        if (bool) {
          finalWordsList.put(zx._1, List(word))
        }
      }
    }
    finalWordsList
  }
}
