package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad._
import bakaev.vad.filters.{DateFilter, OperationFilter}
import bakaev.vad.printers.StatesPrinter
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TransactionStorageTest extends FlatSpec with Matchers with MockitoSugar {

  "A TransactionStorage" should "invoke statePrinter with from to dates on printStatements" in {
    val printer = mock[StatesPrinter]

    val storage = TransactionStorage()
    val from = LocalDate.MIN
    val to = LocalDate.MAX

    val dateFilter = new DateFilter(from, to)
    val operationFilter = new OperationFilter(ALL)

    doNothing().when(printer).printWithFilters(dateFilter, operationFilter, Nil)

    storage.printStatements(dateFilter, operationFilter, printer)

    verify(printer).printWithFilters(dateFilter, operationFilter, Nil)
  }

}
