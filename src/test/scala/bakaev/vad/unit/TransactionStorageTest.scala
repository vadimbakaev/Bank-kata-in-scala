package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad._
import bakaev.vad.domain.filters.{DateFilter, OperationFilter}
import bakaev.vad.domain.printers.StatesPrinter
import bakaev.vad.domain.{PositiveAmount, Transaction, TransactionStorage}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TransactionStorageTest extends FlatSpec with Matchers with MockitoSugar {

  "A TransactionStorage" should "invoke statePrinter with from to dates on printStatements" in {
    val printer = mock[StatesPrinter]

    val storage = TransactionStorage()
    val from    = LocalDate.MIN
    val to      = LocalDate.MAX

    val dateFilter      = new DateFilter(from, to)
    val operationFilter = new OperationFilter(ALL)

    doNothing().when(printer).printWithFilters(dateFilter, operationFilter, Nil)

    storage.printStatements(dateFilter, operationFilter, printer)

    verify(printer).printWithFilters(dateFilter, operationFilter, Nil)
  }

  it should "throw an exception if apply TransactionStorage is null" in {
    the[IllegalArgumentException] thrownBy {
      TransactionStorage(null)
    } should have message "requirement failed: Transactions cannot be null in TransactionStorage"
  }

}
