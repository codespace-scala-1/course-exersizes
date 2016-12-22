package yuri.phonecode

import scala.io.Source
import scala.util.Try

class ReverseLookup(path: String) {

  val lookup = {
    Source
      .fromFile(path)
      .getLines
      .map { rawLine => (rawLine.replaceAll("\"", "").flatMap(c => encodeChar(c)), rawLine) }
      .foldLeft(Map.empty[String, Seq[String]]) {
        case (acc, (k, v)) => acc.updated(k, acc.getOrElse(k, Seq.empty[String]) ++ Seq(v))
      }
  }

  def encodeChar(c: Char): String =
    c.toLower match {
      case 'e' => "0"
      case 'j' | 'n' | 'q' => "1"
      case 'r' | 'w' | 'x' => "2"
      case 'd' | 's' | 'y' => "3"
      case 'f' | 't' => "4"
      case 'a' | 'm' => "5"
      case 'c' | 'i' | 'v' => "6"
      case 'b' | 'k' | 'u' => "7"
      case 'l' | 'o' | 'p' => "8"
      case 'g' | 'h' | 'z' => "9"
    }

}
