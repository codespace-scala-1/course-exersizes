package com.codespace.maxim.scope

import scala.reflect.macros.blackbox.Context

/**
  * Created by Admin on 15.12.2016.
  */

object Macros {
  def contextImpl(c: Context)(block: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._

    val blockCode =
      block.tree
        .toString()
        .replaceAll("dscope.this.`package`.", "")

    val newTree = c.parse("ScopeContext() { scope => " + blockCode + "}")
    println(show(newTree))

    c.Expr[Unit](q" $newTree")
  }
}
