package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class TransactionStorage(private val stateLines: Seq[Transaction]) {
  require(stateLines != null)

  private lazy val chainOfStates =
    stateLines.sorted.indices.map(index => stateLines(index).toState(stateLines.take(index))).reverse

  def deposit(amount: Amount, date: LocalDate): TransactionStorage =
    TransactionStorage(stateLines :+ new Transaction(amount, date))

  def withdraw(amount: Amount, date: LocalDate): TransactionStorage =
    TransactionStorage(stateLines :+ new Transaction(-amount, date))

  def printStatements(printer: StatePrinter): Unit = printer.print(chainOfStates)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: TransactionStorage => stateLines == that.stateLines
      case _                        => false
    }

  override def hashCode: Int = Objects.hashCode(stateLines)
}

object TransactionStorage {
  def apply(): TransactionStorage = new TransactionStorage(Nil)

  def apply(stateLines: Seq[Transaction]): TransactionStorage = new TransactionStorage(stateLines)
}
