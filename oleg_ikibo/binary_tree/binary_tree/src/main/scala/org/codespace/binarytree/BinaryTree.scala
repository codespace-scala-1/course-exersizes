package org.codespace.binarytree

abstract class BinaryTree[T <: Comparable[T]] {

  def add(value : T) : BinaryTree[T]

}

class EmptyTree[T <: Comparable[T]] extends BinaryTree[T] {

  def add(value : T) = new FilledTree(value, new EmptyTree, new EmptyTree)

}

class FilledTree[T <: Comparable[T]] ( val value : T,
                                       val left : BinaryTree[T] = new EmptyTree[T],
                                       val right : BinaryTree[T] = new EmptyTree[T]
                                     ) extends BinaryTree[T] {

  def add(toAdd : T) =
    if (value.compareTo(this.value) > 0)
       new FilledTree[T](this.value, this.left, right.add(value))
    else if (value.compareTo(this.value) < 0)
      new FilledTree[T](this.value, this.left.add(value), right)
    else
      this
}

object Main {

  def main(args : Array[String]) : Unit = {

    val intTree : BinaryTree[Integer] = new FilledTree[Integer](12)
    val intTree1 = intTree.add(15).add(15)
    val i = 4
  }

}
