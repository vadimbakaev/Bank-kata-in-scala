package bakaev.vad

import java.time.LocalDate
import java.util.Objects

class Account(private val sate: AccountState,
              private val transactionPrinter: TransactionPrinter = new TransactionPrinter(System.out)) {

  def printStatement(): Unit = sate.printStatement(transactionPrinter)

  def deposit(amount: Amount, date: LocalDate): Account = new Account(sate.deposit(amount, date), transactionPrinter)

  def withdraw(amount: Amount, date: LocalDate): Account = new Account(sate.withdraw(amount, date), transactionPrinter)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Account => sate == that.sate && transactionPrinter == that.transactionPrinter
      case _             => false
    }

  override def hashCode: Int = Objects.hash(sate, transactionPrinter)
}
