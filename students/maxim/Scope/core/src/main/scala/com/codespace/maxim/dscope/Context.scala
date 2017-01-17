package com.codespace.maxim

import com.codespace.maxim.scope.Macros

/**
  * Created by Maxim Sambulat
  */

package object dscope {

  import scala.language.experimental.macros

  sealed trait State

  case object success extends State

  case object failure extends State

  case object exit extends State

  def Context(block: => Unit): Unit = macro Macros.contextImpl

  def scope(state: State)(action: => Unit): Unit = ???
}

package dscope {

  import scala.annotation.tailrec

  class ScopeContext {

    class scope {
      def apply(state: State)(action: => Unit): Unit = {
        def append(list: List[(Unit => Unit)], action: Unit => Unit): List[(Unit => Unit)] = {
          action :: list
        }

        state match {
          case `success` => actionSuccess = append(actionSuccess, (Unit) => action)
          case `failure` => actionFailure = append(actionFailure, (Unit) => action)
          case `exit` => actionExit = append(actionExit, (Unit) => action)
        }
      }
    }

    private var actionFailure: List[(Unit => Unit)] = Nil
    private var actionSuccess: List[(Unit => Unit)] = Nil
    private var actionExit: List[(Unit => Unit)] = Nil

    private val scopeInst = new scope

    def apply(block: (scope => Unit)): Unit = {
      try {
        block(scopeInst)
        if (actionSuccess.nonEmpty) run(actionSuccess)
      } catch {
        case _: Exception => if (actionFailure.nonEmpty) run(actionFailure)
      } finally {
        if (actionExit.nonEmpty) run(actionExit)
      }
    }

    @tailrec
    private def run(actions: List[(Unit => Unit)]): Unit = {
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

}