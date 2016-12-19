package codespace.phonecode

import scala.io.Source

object App {
  def main(args: Array[String]): Unit = {

    val phonecode = Source.fromFile("/Users/johnsmith/IdeaProjects/course-exersizes/vukelan/PhoneCode/data/phonecode.txt", "UTF-8")
    val lineIterator = phonecode.getLines

    for (line <- lineIterator) {

    }

  }
}
