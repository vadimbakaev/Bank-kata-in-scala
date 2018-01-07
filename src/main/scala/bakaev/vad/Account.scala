package bakaev.vad

import java.time.LocalDate
import java.util.Objects

import bakaev.vad.Amount.PositiveAmount
import bakaev.vad.enums.Operation.{ALL, Operation}

class Account(private val transactionStorage: TransactionStorage, private val statePrinter: StatePrinter) {
  require(transactionStorage != null, "TransactionStorage cannot be null in Account")
  require(statePrinter != null, "StatePrinter cannot be null in Account")

  def deposit(amount: PositiveAmount, date: LocalDate): Account = {
    require(amount != null, "Amount cannot be null in deposit")
    require(date != null, "Date cannot be null in deposit")
    new Account(transactionStorage.deposit(amount, date), statePrinter)
  }

  def withdraw(amount: PositiveAmount, date: LocalDate): Account = {
    require(amount != null, "Amount cannot be null in withdraw")
    require(date != null, "Date cannot be null in withdraw")
    new Account(transactionStorage.withdraw(amount, date), statePrinter)
  }

  def transfer(amount: PositiveAmount, date: LocalDate, receiver: Account): (Account, Account) = {
    require(receiver != null, "Receiver cannot be null in transfer")
    require(receiver != this, "Receiver cannot be sender in transfer")
    require(amount != null, "Amount cannot be null in transfer")
    require(date != null, "Date cannot be null in transfer")
    (this.withdraw(amount, date), receiver.deposit(amount, date))
  }

  def printStatement(from: LocalDate = LocalDate.MIN, to: LocalDate = LocalDate.MAX, toPrint: Operation = ALL): Unit = {
    require(from != null, "From cannot be null in printStatement")
    require(to != null, "To cannot be null in printStatement")
    require(toPrint != null, "ToPrint cannot be null in printStatement")
    transactionStorage.printStatements(from, to, toPrint, statePrinter)
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: Account => transactionStorage == that.transactionStorage && statePrinter == that.statePrinter
    case _             => false
  }

  override def hashCode: Int = Objects.hashCode(transactionStorage, statePrinter)
}
