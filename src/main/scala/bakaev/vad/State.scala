package bakaev.vad

import java.time.LocalDate

import bakaev.vad.Amount.{NegativeAmount, PositiveAmount, ZeroAmount}

class State(
    private val localDate: LocalDate,
    private val operation: Amount,
    private val balance: Amount
) {
  require(!operation.isInstanceOf[ZeroAmount])

  def printOn(printer: StatePrinter): Unit = operation match {
    case positive: PositiveAmount => printer.printLine(localDate, positive, balance)
    case negative: NegativeAmount => printer.printLine(localDate, negative, balance)
    case _                        => throw new IllegalArgumentException("requirement failed: state operation can't be zero")
  }

}
