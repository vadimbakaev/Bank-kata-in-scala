package bakaev.vad.domain.printers

import java.time.LocalDate
import java.util.function.Predicate

import bakaev.vad.domain.{Amount, NotZeroAmount}

trait StateLinePrinter {
  def print(date: LocalDate, operation: NotZeroAmount, balance: Amount): Unit
}

trait StateLinePrinterProducer {
  def stateLinePrinter(dateFilter: Predicate[LocalDate], operationFilter: Predicate[NotZeroAmount]): StateLinePrinter
}
