package com.phonecode

import scala.collection.immutable.Seq

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


  def numberToChars(n: String): List[Char] = {
    var resultList = List(' ')

    val x = n.toCharArray.filter(_ != '-').filter(_ != '/')

    for (z <- x) {
      if (z == '0') {
        resultList = resultList ++ zeroEncode
      }
      if (z == '1') {
        resultList = resultList ++ oneEncode
      }
      if (z == '2') {
        resultList = resultList ++ twoEncode
      }
      if (z == '3') {
        resultList = resultList ++ threeEncode
      }
      if (z == '4') {
        resultList = resultList ++ fourEncode
      }
      if (z == '5') {
        resultList = resultList ++ fiveEncode
      }
      if (z == '6') {
        resultList = resultList ++ sixEncode
      }
      if (z == '7') {
        resultList = resultList ++ sevenEncode
      }
      if (z == '8') {
        resultList = resultList ++ eightEncode
      }
      if (z == '9') {
        resultList = resultList ++ nineEncode
      }
    }
    resultList.sorted
  }

  def toWord(arrayToCheck: List[Char]): List[String] = {
    var finalWordsList: List[String] = List("")
    for (word <- Dictionary.words) {
      val wordSet = word.toCharArray.filter(_ != '-').filter(_ != '/').toSet
      val arrayToCheckSet = arrayToCheck.toSet
      val bool = wordSet.subsetOf(arrayToCheckSet)
      if (bool) {
        finalWordsList = finalWordsList ++ List(word)
      }
    }
    finalWordsList
  }
}
