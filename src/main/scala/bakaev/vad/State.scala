package bakaev.vad

import java.time.LocalDate
import java.util.Objects

trait State {
  def printMatchedOn(from: LocalDate, to: LocalDate, toPrint: Operation, printer: StatePrinter): Unit
}

object State {
  def apply(localDate: LocalDate, operation: NotZeroAmount, balance: Amount): State =
    new StateImpl(localDate, operation, balance)

  class StateImpl(private val operationDate: LocalDate,
                  private val operation: NotZeroAmount,
                  private val balance: Amount)
      extends State {
    require(operationDate != null, "OperationDate cannot be null in StateImpl")
    require(operation != null, "Operation cannot be null in StateImpl")
    require(balance != null, "Balance cannot be null in StateImpl")
    def printMatchedOn(from: LocalDate, to: LocalDate, toPrint: Operation, printer: StatePrinter): Unit =
      if (operationDate.isAfter(from) && operationDate.isBefore(to)) printMatchedByOperationOn(printer, toPrint)

    private def printMatchedByOperationOn(printer: StatePrinter, toPrint: Operation): Unit = operation match {
      case positive: PositiveAmount =>
        if (toPrint == ALL || toPrint == CREDIT) printer printLine (operationDate, positive, balance)
      case negative: NegativeAmount =>
        if (toPrint == ALL || toPrint == DEBIT) printer printLine (operationDate, negative, balance)
    }

    override def equals(obj: scala.Any): Boolean = obj match {
      case that: StateImpl => operationDate == that.operationDate && operation == that.operation
      case _               => false
    }

    override def hashCode: Int = Objects.hashCode(operationDate, operation)
  }

}
