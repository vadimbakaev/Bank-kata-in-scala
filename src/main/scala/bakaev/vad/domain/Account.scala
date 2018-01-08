package bakaev.vad.domain

import java.time.LocalDate
import java.util.Objects

import bakaev.vad.domain.filters.{DateFilter, OperationFilter}
import bakaev.vad.domain.printers.StatesPrinter
import bakaev.vad.{ALL, Operation}

class Account(
    private val transactionStorage: TransactionStorage = TransactionStorage(),
    private val statePrinter: StatesPrinter = new StatesPrinter(System.out)
) {
  require(transactionStorage != null, "TransactionStorage cannot be null in Account")
  require(statePrinter != null, "StatesPrinter cannot be null in Account")
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
    require(receiver ne this, "Receiver cannot be sender in transfer")
    require(amount != null, "Amount cannot be null in transfer")
    require(date != null, "Date cannot be null in transfer")
    (this.withdraw(amount, date), receiver.deposit(amount, date))
  }

  def printStatement(after: LocalDate = LocalDate.MIN,
                     before: LocalDate = LocalDate.MAX,
                     canPrint: Operation = ALL): Unit = {
    require(after != null, "After cannot be null in printStatement")
    require(before != null, "Before cannot be null in printStatement")
    require(canPrint != null, "CanPrint cannot be null in printStatement")
    transactionStorage.printStatements(new DateFilter(after, before), new OperationFilter(canPrint), statePrinter)
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: Account => transactionStorage == that.transactionStorage && statePrinter == that.statePrinter
    case _             => false
  }

  override def hashCode: Int = Objects.hash(transactionStorage, statePrinter)
}
