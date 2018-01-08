package bakaev.vad

import java.io.PrintStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Objects

import bakaev.vad.StatePrinter._

class StatePrinter(private val printStream: PrintStream) {
  require(printStream != null, "PrintStream cannot be null in StatePrinter")
  def print(from: LocalDate, to: LocalDate, toPrint: Operation, states: Seq[State]): Unit = {
    printStream println Header
    states foreach (state => state printMatchedOn (from, to, toPrint, this))
  }

  def printLine(date: LocalDate, debit: NegativeAmount, balance: Amount): Unit =
    printStream println s"${dateBlock(date)} ||          || ${operationBlock(debit)}|| ${balance.moneyRepresentation}"

  def printLine(date: LocalDate, credit: PositiveAmount, balance: Amount): Unit =
    printStream println s"${dateBlock(date)} || ${operationBlock(credit)}||          || ${balance.moneyRepresentation}"

  private def dateBlock(date: LocalDate) = date format Formatter

  private def operationBlock(creditOrDebit: NotZeroAmount) =
    (creditOrDebit.moneyAbsoluteRepresentation padTo (OperationBlockPadding, " ")).mkString

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: StatePrinter => printStream == that.printStream
    case _                  => false
  }

  override def hashCode: Int = Objects.hashCode(printStream)
}

object StatePrinter {
  val Header: String               = "date       || credit   || debit    || balance"
  val Formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
  val OperationBlockPadding: Int   = 9
}
