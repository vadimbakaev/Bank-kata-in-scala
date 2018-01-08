package bakaev.vad.domain

import java.time.LocalDate
import java.util.Objects
import java.util.function.Predicate

import bakaev.vad.domain.printers.StatesPrinter

class TransactionStorage private (private val sortedTransactions: Seq[Transaction]) {
  require(sortedTransactions != null, "SortedTransactions cannot be null in TransactionStorage")

  private lazy val chainOfStates = sortedTransactions.indices
    .map(index => sortedTransactions(index) toStateLine (sortedTransactions take index))
    .reverse

  def deposit(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(sortedTransactions :+ Transaction(amount, date))

  def withdraw(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(sortedTransactions :+ Transaction(-amount, date))

  def printStatements(dateFilter: Predicate[LocalDate],
                      operationFilter: Predicate[NotZeroAmount],
                      printer: StatesPrinter): Unit =
    printer printWithFilters (dateFilter, operationFilter, chainOfStates)

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
