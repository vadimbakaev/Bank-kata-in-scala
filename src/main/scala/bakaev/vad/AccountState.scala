package bakaev.vad

import java.time.LocalDate

class AccountState {

  def deposit(amount: Amount, date: LocalDate): AccountState = ???

  def withdraw(amount: Amount, date: LocalDate): AccountState = ???

  def printStatement(printer: StatePrinter): Unit = ???

}
