package codespace.ticktack.strategies

import codespace.ticktack.{Label,Rules,Player}

abstract class BasePlayer(val label:Label, val rules: Rules) extends Player

