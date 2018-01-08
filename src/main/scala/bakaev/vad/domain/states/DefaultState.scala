package bakaev.vad.domain.states

import java.time.LocalDate
import java.util.Objects

import bakaev.vad.domain.printers.StateLinePrinter
import bakaev.vad.domain.{Amount, NotZeroAmount}

class DefaultState(
    private val operationDate: LocalDate,
    private val operation: NotZeroAmount,
    private val balance: Amount
) extends State {

  require(operationDate != null, "OperationDate cannot be null in StateImpl")
  require(operation != null, "Operation cannot be null in StateImpl")
  require(balance != null, "Balance cannot be null in StateImpl")

  override def printOn(printer: StateLinePrinter): Unit = printer print (operationDate, operation, balance)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: DefaultState => operationDate == that.operationDate && operation == that.operation
    case _                  => false
  }

  override def hashCode: Int = Objects.hashCode(operationDate, operation)
}

object DefaultState {
  def apply(localDate: LocalDate, operation: NotZeroAmount, balance: Amount): DefaultState =
    new DefaultState(localDate, operation, balance)
}
