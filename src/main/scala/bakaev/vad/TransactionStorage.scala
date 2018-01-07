package bakaev.vad

import java.time.LocalDate

import bakaev.vad.Amount.PositiveAmount

class TransactionStorage(private val stateLines: Seq[Transaction]) {
  require(stateLines != null)

  private lazy val chainOfStates =
    stateLines.sorted.indices.map(index => stateLines(index).toState(stateLines.take(index))).reverse

  def deposit(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(stateLines :+ Transaction(amount, date))

  def withdraw(amount: PositiveAmount, date: LocalDate): TransactionStorage =
    TransactionStorage(stateLines :+ Transaction(-amount, date))

  def printStatements(printer: StatePrinter): Unit = printer.print(chainOfStates)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: TransactionStorage => stateLines == that.stateLines
    case _                        => false
  }

  override def hashCode: Int = java.util.Objects.hashCode(stateLines)
}

object TransactionStorage {
  def apply(): TransactionStorage = new TransactionStorage(Nil)

  def apply(stateLines: Seq[Transaction]): TransactionStorage = new TransactionStorage(stateLines)
}
