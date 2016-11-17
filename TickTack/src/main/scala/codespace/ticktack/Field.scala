package codespace.ticktack

trait Field {

  def get(i:Int,j:Int):Option[Label]

  def put(i:Int,j:Int,l:Label): Field

}

