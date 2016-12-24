package com.codespace.maxim

import com.codespace.maxim.scope.Macros


package object dscope {

  import scala.language.experimental.macros

  def Context(block: => Unit): Unit = macro Macros.contextImpl

  def scope(state: State)(action: Unit): Unit = ???// macro Macros.scopeImpl

}

package dscope {

  import scala.annotation.tailrec

  sealed trait State
  case object success extends State
  case object failure extends State
  case object exit extends State

  class ScopeContext {

    var actionFailure: List[(() => Unit)] = Nil
    var actionSuccess: List[(() => Unit)] = Nil
    var actionExit: List[(() => Unit)] = Nil

    val scopeInst = new scope(this)

    def apply(block: (scope => Unit)): Unit = {
      try {
        block(scopeInst)
        if (actionSuccess.nonEmpty) run(actionSuccess)
      } catch {
        case ex: Exception => if (actionFailure.nonEmpty) run(actionFailure)
                              throw ex
      } finally {
        if (actionExit.nonEmpty) run(actionExit)
      }
    }

    @tailrec
    private def run(actions: List[(() => Unit)]): Unit = {
      actions match {
        case head :: tail => {
          head()
          run(tail)
        }
        case Nil =>
      }
    }
  }

  object ScopeContext {
    def apply(): ScopeContext = new ScopeContext()
  }

    class scope(owner:ScopeContext) {

      import owner._
      def apply(state: State)(action: => Unit): Unit = {
        state match {
          case `success` => owner.actionSuccess = (() => action)::owner.actionSuccess
          case `failure` => actionFailure = (() => action)::actionFailure
          case `exit` => actionExit = (() => action)::actionExit
        }
      }


    }

}
