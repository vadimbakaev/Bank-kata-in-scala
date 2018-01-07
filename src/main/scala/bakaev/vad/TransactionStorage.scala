package bakaev.vad

import java.time.LocalDate
import java.util.Objects

import bakaev.vad.Amount.PositiveAmount

class TransactionStorage(private val transactions: Seq[Transaction]) {
  require(transactions != null)

  private lazy val chainOfStates = transactions.sorted.indices
    .map(index => transactions(index).toState(transactions.take(index)))
    .reverse

  def deposit(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(transactions :+ Transaction(amount, date))

  def withdraw(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(transactions :+ Transaction(-amount, date))

  def printStatements(printer: StatePrinter): Unit = printer.print(chainOfStates)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: TransactionStorage => transactions == that.transactions
    case _                        => false
  }

  override def hashCode: Int = Objects.hashCode(transactions)
}

object TransactionStorage {
  def apply(stateLines: Seq[Transaction] = Nil): TransactionStorage = new TransactionStorage(stateLines)
}
