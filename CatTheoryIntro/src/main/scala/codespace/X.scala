package codespace

object X {


  def main(args: Array[String]): Unit =
  {
    var name: Option[String]=None
    var i = 0
    while(i < args.length) {
      if (args(i) == "--name") {
        i=i+1
        name = Some(args(i))
      }
      i += 1
    }

    name foreach { x =>
      Console.println(x)
    }

    name exists { _ < "Joe"  }

    if (name.isDefined) {
      Console.println(name.get)
    }


  }


}
