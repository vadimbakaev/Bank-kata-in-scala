package bakaev.vad.domain.filters

import java.time.LocalDate
import java.util.Objects
import java.util.function.Predicate

class DateFilter(private val from: LocalDate, private val to: LocalDate) extends Predicate[LocalDate] {
  override def test(date: LocalDate): Boolean = (date isAfter from) && (date isBefore to)

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: DateFilter => from == that.from && to == that.to
    case _                => false
  }

  override def hashCode: Int = Objects.hash(from, to)
}
