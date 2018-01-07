package bakaev.vad

import java.io.PrintStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import bakaev.vad.Amount.{NegativeAmount, PositiveAmount}
import bakaev.vad.StatePrinter._

class StatePrinter(private val printStream: PrintStream) {

  def print(states: Seq[State]): Unit = {
    printStream.println(Header)
    states.foreach {
      _.printOn(this)
    }
  }

  def printLine(date: LocalDate, debit: NegativeAmount, balance: Amount): Unit =
    printStream.println(
      s"${date.format(Formatter)} ||          || ${operationBlock(debit)}|| ${balance.moneyRepresentation}"
    )

  def printLine(date: LocalDate, credit: PositiveAmount, balance: Amount): Unit =
    printStream.println(
      s"${date.format(Formatter)} || ${operationBlock(credit)}||          || ${balance.moneyRepresentation}"
    )

  private def operationBlock(creditOrDebit: NotZeroAmount) =
    creditOrDebit.moneyAbsRepresentation.padTo(OperationBlockPadding, " ").mkString

}

object StatePrinter {
  val Header: String               = "date       || credit   || debit    || balance"
  val Formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
  val OperationBlockPadding: Int   = 9
}
