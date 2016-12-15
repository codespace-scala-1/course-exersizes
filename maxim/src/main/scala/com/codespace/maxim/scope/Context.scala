package com.codespace.maxim

/**
  * Created by Maxim Sambulat
  */

package object scope {

  sealed trait State

  case object success extends State

  case object failure extends State

  case object exit extends State

}

package scope {

  class Context {

    class scope {
      def apply(state: State)(action: => Unit): Unit = {
        state match {
          case s if s == success => actionSuccess = (_) => action
          case f if f == failure => actionFailure = (_) => action
          case e if e == exit => actionExit = (_) => action
        }
      }
    }

    private var actionFailure: (Unit => Unit) = _
    private var actionSuccess: (Unit => Unit) = _
    private var actionExit: (Unit => Unit) = _

    private val scopeInst = new scope

    def apply(block: (scope => Unit)): Unit = {
      println("class DScope apply")
      try {
        block(scopeInst)
        if (actionSuccess != null) actionSuccess()
      } catch {
        case _: Exception => if (actionFailure != null) actionFailure()
      } finally {
        if (actionExit != null) actionExit()
      }
    }
  }

  object Context {
    def apply(): Context = {
      println("object DScope apply")
      new Context()
    }
  }

}