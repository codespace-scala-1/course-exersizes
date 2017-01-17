package codespace.ticktack.strategies

import codespace.ticktack.{Label, Player, Rules}

abstract class BasePlayer(val label: Label, val rules: Rules) extends Player

