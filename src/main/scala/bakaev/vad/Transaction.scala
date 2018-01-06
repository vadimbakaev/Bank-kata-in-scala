package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class Transaction(private val operation: Amount, private val date: LocalDate) extends Ordered[Transaction] {

  def toState(previousTransactions: Seq[Transaction]): State =
    new State(date, operation, previousTransactions.map(_.operation).fold(operation)(_ + _))

  override def compare(that: Transaction): Int = date.compareTo(that.date)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Transaction => date == that.date && operation == that.operation
      case _                 => false
    }

  override def hashCode: Int = Objects.hashCode(date, operation)

}
