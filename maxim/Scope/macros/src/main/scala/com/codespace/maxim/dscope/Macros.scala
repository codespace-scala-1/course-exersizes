package com.codespace.maxim.scope

import scala.reflect.macros.blackbox.Context

/**
  * Created Maxim Sambulat
  */

object Macros {
  def contextImpl(c: Context)(block: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._

    val oldBlockCode = showCode(block.tree)
    println("=============================== Old Code ===============================")
    println(oldBlockCode)

    val newBlockCode = oldBlockCode.replaceAll("com.codespace.maxim.dscope.`package`.", "")
    println("=============================== Cleared Code ===============================")
    println(newBlockCode)

    val newTree = c.parse(s"ScopeContext() { scope => $newBlockCode }")
    println("=============================== New Code ===============================")
    println(showCode(newTree))

    c.Expr[Unit](q" $newTree")
  }
}
