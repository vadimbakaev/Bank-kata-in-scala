package bakaev.vad

import java.time.LocalDate
import java.util.Objects

import bakaev.vad.Amount.{NegativeAmount, PositiveAmount, ZeroAmount}

sealed trait Transaction extends Ordered[Transaction] {

  protected val value: Amount
  protected val date: LocalDate

  def toState(previousTransactions: Seq[Transaction]): State

  override def compare(that: Transaction): Int = date.compareTo(that.date)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Transaction => date == that.date && value == that.value
      case _                 => false
    }

  override def hashCode: Int = Objects.hashCode(date, value)
}

object Transaction {

  def apply(value: Amount, date: LocalDate): Transaction = value match {
    case _: ZeroAmount     => throw new IllegalArgumentException("requirement failed: transaction can't be zero")
    case _: PositiveAmount => new Credit(value, date)
    case _: NegativeAmount => new Debit(value, date)
  }

  class Debit(override protected val value: Amount, override protected val date: LocalDate) extends Transaction {
    override def toState(previousTransactions: Seq[Transaction]): State =
      new State(date, value, previousTransactions.map(_.value).fold(value)(_ + _))
  }

  class Credit(override protected val value: Amount, override protected val date: LocalDate) extends Transaction {
    override def toState(previousTransactions: Seq[Transaction]): State =
      new State(date, value, previousTransactions.map(_.value).fold(value)(_ + _))
  }

}
