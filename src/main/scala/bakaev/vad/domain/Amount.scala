package bakaev.vad.domain

import java.util.Objects

sealed trait Amount {
  protected val value: Int

  def unary_- : Amount

  def moneyAbsoluteRepresentation: String = s"${Math.abs(value)}.00"

  def moneyRepresentation: String = s"$value.00"

  def +[T <: Amount](that: T): Amount = Amount(value + that.value)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: Amount => value == that.value
    case _            => false
  }

  override def hashCode: Int = Objects.hashCode(value)
}

object ZeroAmount extends Amount {
  override protected val value: Int = 0

  override def +[T <: Amount](that: T): T = that

  override def unary_- : ZeroAmount.type = ZeroAmount
}

trait NotZeroAmount extends Amount

object NotZeroAmount {
  def apply(value: Int): NotZeroAmount = value match {
    case positive if positive > 0 => new PositiveAmount(positive)
    case negative if negative < 0 => new NegativeAmount(negative)
    case _                        => throw new IllegalArgumentException("requirement failed: NotZeroAmount can't be zero")
  }
}

class PositiveAmount(override protected val value: Int) extends NotZeroAmount {
  require(value > 0)
  override def unary_- : NegativeAmount = new NegativeAmount(-value)
}

class NegativeAmount(override protected val value: Int) extends NotZeroAmount {
  require(value < 0)
  override def unary_- : PositiveAmount = new PositiveAmount(-value)
}

object Amount {
  def apply(value: Int): Amount = value match {
    case 0             => ZeroAmount
    case notZeroAmount => NotZeroAmount(notZeroAmount)
  }
}
