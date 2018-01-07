package bakaev.vad

import java.time.LocalDate
import java.util.Objects

import bakaev.vad.Amount.{NegativeAmount, NotZeroAmount, PositiveAmount}

trait State {
  def printMatchedOn(from: LocalDate, to: LocalDate, printer: StatePrinter): Unit
}

object State {
  def apply(localDate: LocalDate, operation: NotZeroAmount, balance: Amount): State =
    new StateImpl(localDate, operation, balance)

  class StateImpl(private val operationDate: LocalDate,
                  private val operation: NotZeroAmount,
                  private val balance: Amount)
      extends State {
    def printMatchedOn(from: LocalDate, to: LocalDate, printer: StatePrinter): Unit =
      if (operationDate.isAfter(from) && operationDate.isBefore(to)) {
        operation match {
          case positive: PositiveAmount => printer printLine (operationDate, positive, balance)
          case negative: NegativeAmount => printer printLine (operationDate, negative, balance)
        }
      }

    override def equals(obj: scala.Any): Boolean = obj match {
      case that: StateImpl => operationDate == that.operationDate && operation == that.operation
      case _               => false
    }

    override def hashCode: Int = Objects.hashCode(operationDate, operation)
  }

}
