package com.phonecode

class Encoder {

  def numberToLetter(n: String): String = {
    val charArray = n.toCharArray

   // val x = charArray.filter(x => x != '-' && x != '/')
    val x = charArray.filter(_ != '-').filter(_ != '/')




  }
