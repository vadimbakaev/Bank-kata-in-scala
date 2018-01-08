package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class TransactionStorage private (private val sortedTransactions: Seq[Transaction]) {
  require(sortedTransactions != null, "SortedTransactions cannot be null in StateImpl")

  private lazy val chainOfStates = sortedTransactions.indices
    .map(index => sortedTransactions(index) toState (sortedTransactions take index))
    .reverse

  def deposit(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(sortedTransactions :+ Transaction(amount, date))

  def withdraw(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(sortedTransactions :+ Transaction(-amount, date))

  def printStatements(from: LocalDate, to: LocalDate, toPrint: Operation, printer: StatePrinter): Unit =
    printer print (from, to, toPrint, chainOfStates)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: TransactionStorage => sortedTransactions == that.sortedTransactions
    case _                        => false
  }

  override def hashCode: Int = Objects.hashCode(sortedTransactions)
}

object TransactionStorage {
  def apply(transactions: Seq[Transaction] = Nil): TransactionStorage = {
    require(transactions != null, "Transactions cannot be null in TransactionStorage")
    new TransactionStorage(transactions.sorted)
  }
}
