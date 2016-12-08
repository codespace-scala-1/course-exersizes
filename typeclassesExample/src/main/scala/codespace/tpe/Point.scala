package codespace.tpe

object Implicits
{

  trait JsonSerializer[T] {
    def toJson(x:T):Json
  }

  trait XmlSerializer[T] {
    def toXml(x:T):Xml
  }

  implicit object PointXmlSerializer extends XmlSerializer[Point]
  {
    def toXml(x:Point):Xml = ???
  }


}

case class Point(x:Int, y:Int)


trait Json
trait Xml



object X
{
  import Implicits._


  def useXml[T](p:T)(implicit m:XmlSerializer[T]): Unit =
  {
    System.out.println(m.toXml(p))
  }

  def useJson[T](p:Point): Unit = {
    ???
  }


}

object XClient {
  X.useXml(Point(2, 3))
}