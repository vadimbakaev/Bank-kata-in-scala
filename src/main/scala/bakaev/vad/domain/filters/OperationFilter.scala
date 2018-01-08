package bakaev.vad.domain.filters

import java.util.Objects
import java.util.function.Predicate

import bakaev.vad._
import bakaev.vad.domain.{NegativeAmount, NotZeroAmount, PositiveAmount}

class OperationFilter(private val printPermission: Operation) extends Predicate[NotZeroAmount] {
  override def test(operation: NotZeroAmount): Boolean = (printPermission, operation) match {
    case (ALL, _)                    => true
    case (CREDIT, _: PositiveAmount) => true
    case (DEBIT, _: NegativeAmount)  => true
    case _                           => false
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: OperationFilter => printPermission == that.printPermission
    case _                     => false
  }

  override def hashCode: Int = Objects.hash(printPermission)
}
