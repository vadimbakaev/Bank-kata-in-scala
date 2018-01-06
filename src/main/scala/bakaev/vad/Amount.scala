package bakaev.vad

import java.util.Objects

class Amount(private val value: Int) {

  def isNegative: Boolean = value < 0

  def moneyRepresentation: String = s"$value.00"

  def +(that: Amount): Amount = new Amount(value + that.value)

  def unary_- : Amount = new Amount(-value)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Amount => value == that.value
      case _            => false
    }

  override def hashCode: Int = Objects.hashCode(value)
}

object Amount {
  def apply(value: Int): Amount = new Amount(value)

  val ZeroAmount: Amount = this(0)
}
