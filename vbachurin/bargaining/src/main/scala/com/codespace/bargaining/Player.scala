package com.codespace.bargaining

import com.codespace.bargaining.strategies.PlayerStrategy

case class Player(id: String, balance: Int, strategy: PlayerStrategy)
