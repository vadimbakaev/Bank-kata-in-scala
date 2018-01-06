package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class Account(private val sate: TransactionStorage, private val statePrinter: StatePrinter) {

  def printStatement(): Unit = sate.printStatements(statePrinter)

  def deposit(amount: Amount, date: LocalDate): Account = new Account(sate.deposit(amount, date), statePrinter)

  def withdraw(amount: Amount, date: LocalDate): Account = new Account(sate.withdraw(amount, date), statePrinter)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Account => sate == that.sate && statePrinter == that.statePrinter
      case _             => false
    }

  override def hashCode: Int = Objects.hash(sate, statePrinter)
}
