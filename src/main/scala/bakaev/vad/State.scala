package bakaev.vad

import java.time.LocalDate

import bakaev.vad.Amount.{NegativeAmount, NotZeroAmount, PositiveAmount}

class State(
    private val localDate: LocalDate,
    private val operation: NotZeroAmount,
    private val balance: Amount
) {

  def printOn(printer: StatePrinter): Unit = operation match {
    case positive: PositiveAmount => printer.printLine(localDate, positive, balance)
    case negative: NegativeAmount => printer.printLine(localDate, negative, balance)
  }

}
