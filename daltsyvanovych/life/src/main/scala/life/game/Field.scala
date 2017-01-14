package life.game

import com.sun.crypto.provider.AESCipher.AES128_CBC_NoPadding


class Field(size: Int) {

  val data: IndexedSeq[IndexedSeq[Boolean]] = _

  def init() = {
    data = IndexedSeq(size).map(el => IndexedSeq(size))
  }

  def get(x: Int, y: Int): Option[Boolean] = {
      if (x > data.length || y > data(0).length)
  }

  def reset(x: Int, y: Int): Option[Boolean] = {

  }

}
