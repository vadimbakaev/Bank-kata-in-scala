package bakaev.vad

import java.util.Objects

sealed trait Amount {
  protected val value: Int

  def moneyAbsoluteRepresentation: String = s"${Math.abs(value)}.00"

  def moneyRepresentation: String = s"$value.00"

  def +(that: Amount): Amount = Amount(value + that.value)

  def unary_- : Amount = Amount(-value)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: Amount => value == that.value
    case _            => false
  }

  override def hashCode: Int = Objects.hashCode(value)
}

object Amount {
  def apply(value: Int): Amount = value match {
    case 0             => ZeroAmount
    case notZeroAmount => NotZeroAmount(notZeroAmount)
  }

  object ZeroAmount extends Amount {
    override protected val value: Int = 0
  }

  trait NotZeroAmount extends Amount {
    override def unary_- : NotZeroAmount = NotZeroAmount(-value)
  }

  object NotZeroAmount {
    def apply(value: Int): NotZeroAmount = value match {
      case positive if positive > 0 => new PositiveAmount(positive)
      case negative if negative < 0 => new NegativeAmount(negative)
      case _                        => throw new IllegalArgumentException("requirement failed: NotZeroAmount can't be zero")
    }
  }

  class PositiveAmount(override protected val value: Int) extends NotZeroAmount {
    require(value > 0)
  }

  class NegativeAmount(override protected val value: Int) extends NotZeroAmount {
    require(value < 0)
  }

}
