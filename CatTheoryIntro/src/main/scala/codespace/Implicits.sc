import scala.languageFeature.implicitConversions


def f(a:String):Int = {
  Integer.parseInt(a)
}


def g(b:Int)(implicit transformer:String=>Int):Unit =
{

  val x:Int = "123"
  System.out.println(s"b % 2 == ${b%2}")

}

val x: Either[Throwable,Int] = ???
var y: Either[String,Int]

y = x.left.map(_.getMessage)

val getMessage: Throwable => String = _.getMessage

//val x = "123"
g(123)(f)
