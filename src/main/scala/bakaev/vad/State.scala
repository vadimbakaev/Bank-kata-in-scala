package bakaev.vad

import java.time.LocalDate

class State(
    private val localDate: LocalDate,
    private val operation: Amount,
    private val balance: Amount
) {

  def printOn(printer: StatePrinter): Unit = printer.printLine(localDate, operation, balance)

}
