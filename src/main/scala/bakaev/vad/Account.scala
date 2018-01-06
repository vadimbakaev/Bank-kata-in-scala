package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class Account(private val sate: AccountState, private val statePrinter: StatePrinter = new StatePrinter()) {

  def printStatement(): Unit = sate.printStatement(statePrinter)

  def deposit(amount: Amount, date: LocalDate): Account = new Account(sate.deposit(amount, date), statePrinter)

  def withdraw(amount: Amount, date: LocalDate): Account = new Account(sate.withdraw(amount, date), statePrinter)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Account => sate == that.sate && statePrinter == that.statePrinter
      case _             => false
    }

  override def hashCode: Int = Objects.hash(sate, statePrinter)
}
