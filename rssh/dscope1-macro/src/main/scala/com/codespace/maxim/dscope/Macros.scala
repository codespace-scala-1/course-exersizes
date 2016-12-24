package com.codespace.maxim.scope

import scala.reflect.macros.blackbox.Context
import scala.reflect.api._
import com.codespace.maxim.dscope._


object Macros {

  def contextImpl(c: Context)(block: c.Expr[Any]): c.Expr[Unit] = {
    import c.universe._

    //block.tree
    //    .toString()
    //    .replaceAll("dscope.this.`package`.", "")
    val transformer = new Transformer() {
         
          override def transform(tree:Tree):Tree =
           tree match {
             //case q"$f($arg1)($arg2)" => 
             //           System.err.println(s"f=$f")
             //           f match {
                          case q"$com.codespace.maxim.dscope.`package`.scope" =>
                                  q"implicitly[com.codespace.maxim.dscope.scope]"
             //                     q"implicitly[com.codespace.maxim.dscope.scope]($argss)"
             //             case _ => super.transform(tree)
             //           }
             case _ => super.transform(tree)
           }

    }

    val newTree = q"""ScopeContext() { 
                     scope1:com.codespace.maxim.dscope.scope => {
                         implicit val scope2=scope1 
                         ${transformer.transform(block.tree)} 
                       }
                     }
                   """
    println("MACRO:contextImpl:"+show(newTree))

    c.Expr[Unit](newTree)
  }

  def scopeImpl(c: Context)(state:c.Expr[State])(action: c.Expr[Unit]): c.Expr[Unit] = {
    import c.universe._
    val newTree = q"implicitly[com.codespace.maxim.dscope.scope].apply($state)(${action})"
    println("MACRO:scopeImpl:"+show(newTree))
    c.Expr[Unit](newTree)
  }

}
