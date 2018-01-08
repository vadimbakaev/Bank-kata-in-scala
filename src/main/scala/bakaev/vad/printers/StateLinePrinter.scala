package bakaev.vad.printers

import java.time.LocalDate
import java.util.function.Predicate

import bakaev.vad.{Amount, NotZeroAmount}

trait StateLinePrinter {
  def print(date: LocalDate, debit: NotZeroAmount, balance: Amount): Unit
}

trait StateLinePrinterProducer {
  def stateLinePrinter(dateFilter: Predicate[LocalDate], operationFilter: Predicate[NotZeroAmount]): StateLinePrinter
}
