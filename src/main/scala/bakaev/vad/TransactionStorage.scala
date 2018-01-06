package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class TransactionStorage(private val stateLines: Seq[Transaction] = Nil) {

  def deposit(amount: Amount, date: LocalDate): TransactionStorage =
    new TransactionStorage(stateLines :+ new Transaction(amount, date))

  def withdraw(amount: Amount, date: LocalDate): TransactionStorage =
    new TransactionStorage(stateLines :+ new Transaction(-amount, date))

  def printStatements(printer: StatePrinter): Unit = printer.print(
    stateLines.sorted
      .foldLeft((Nil: Seq[Transaction], Nil: Seq[State])) {
        case ((previousTransactions, acc), transaction) =>
          (previousTransactions :+ transaction, transaction.toState(previousTransactions) +: acc)
      }
      ._2
  )

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: TransactionStorage => stateLines == that.stateLines
      case _                        => false
    }

  override def hashCode: Int = Objects.hashCode(stateLines)
}
