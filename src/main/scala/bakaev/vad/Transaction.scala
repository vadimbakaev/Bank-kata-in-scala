package bakaev.vad

import java.time.LocalDate
import java.util.Objects

sealed trait Transaction extends Ordered[Transaction] {

  protected val value: NotZeroAmount
  protected val date: LocalDate

  def toState(previousTransactions: Seq[Transaction]): State =
    State(date, value, (previousTransactions map (transaction => transaction.value)).fold(value: Amount)(_ + _))

  override def compare(that: Transaction): Int = date compareTo that.date

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: Transaction => date == that.date && value == that.value
    case _                 => false
  }

  override def hashCode: Int = Objects.hashCode(date, value)
}

object Transaction {
  def apply(value: NotZeroAmount, date: LocalDate): Transaction = value match {
    case positive: PositiveAmount => new Credit(positive, date)
    case negative: NegativeAmount => new Debit(negative, date)
  }

  class Debit(override protected val value: NegativeAmount, override protected val date: LocalDate) extends Transaction

  class Credit(override protected val value: PositiveAmount, override protected val date: LocalDate) extends Transaction

}
