package bakaev.vad

import java.util.Objects

sealed trait Amount {

  protected val value: Int

  def moneyAbsRepresentation: String = s"${Math.abs(value)}.00"

  def moneyRepresentation: String = s"$value.00"

  def +(that: Amount): Amount = Amount(value + that.value)

  def unary_- : Amount = Amount(-value)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Amount => value == that.value
      case _            => false
    }

  override def hashCode: Int = Objects.hashCode(value)
}

object Amount {

  def apply(value: Int): Amount = value match {
    case positive if positive > 0 => new PositiveAmount(value)
    case 0                        => new ZeroAmount(0)
    case negative                 => new NegativeAmount(negative)
  }

  val ZeroAmount: Amount = this(0)

  class PositiveAmount(override protected val value: Int) extends Amount {
    require(value > 0)
  }

  class NegativeAmount(override protected val value: Int) extends Amount {
    require(value < 0)
  }

  class ZeroAmount(override protected val value: Int) extends Amount {
    require(value == 0)
  }

}
