package bakaev.vad.printers

import java.io.PrintStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Objects
import java.util.function.Predicate

import bakaev.vad._
import bakaev.vad.printers.StatesPrinter._
import bakaev.vad.states.State

class StatesPrinter(private val printStream: PrintStream) extends StateLinePrinterProducer {
  require(printStream != null, "PrintStream cannot be null in StatesPrinter")
  def printWithFilters(dateFilter: Predicate[LocalDate],
                       operationFilter: Predicate[NotZeroAmount],
                       states: Seq[State]): Unit = {
    val filteredLinePrinter = stateLinePrinter(dateFilter, operationFilter)
    printStream println Header
    states foreach (state => state printOn filteredLinePrinter)
  }

  override def stateLinePrinter(dateFilter: Predicate[LocalDate],
                                operationFilter: Predicate[NotZeroAmount]): StateLinePrinter =
    (date: LocalDate, operation: NotZeroAmount, balance: Amount) => {
      if (dateFilter.test(date) && operationFilter.test(operation)) printLine(date, operation, balance)
    }

  private def operationsBlock(operation: NotZeroAmount): String = operation match {
    case positive: PositiveAmount => s"| ${operationBlock(positive)}||          |"
    case negative: NegativeAmount => s"|          || ${operationBlock(negative)}|"
  }

  private def printLine(date: LocalDate, operation: NotZeroAmount, balance: Amount): Unit =
    printStream println s"${date format Formatter} |${operationsBlock(operation)}| ${balance.moneyRepresentation}"

  private def operationBlock(creditOrDebit: NotZeroAmount) =
    (creditOrDebit.moneyAbsoluteRepresentation padTo (OperationBlockPadding, " ")).mkString

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: StatesPrinter => printStream == that.printStream
    case _                   => false
  }

  override def hashCode: Int = Objects.hashCode(printStream)
}

object StatesPrinter {
  val Header: String               = "date       || credit   || debit    || balance"
  val Formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
  val OperationBlockPadding: Int   = 9
}
