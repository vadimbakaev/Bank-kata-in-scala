package bakaev.vad.unit

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad._
import bakaev.vad.domain.filters.{DateFilter, OperationFilter}
import bakaev.vad.domain.printers.StatesPrinter
import org.mockito.Mockito._

class StatesPrinterTest extends BaseSpec {

  "A StatesPrinter" should "print only header for empty list" in {
    val printStream = mock[PrintStream]
    val printer     = new StatesPrinter(printStream)

    val from = LocalDate.MIN
    val to   = LocalDate.MAX

    printer.printWithFilters(new DateFilter(from, to), new OperationFilter(ALL), Nil)

    verify(printStream).println("date       || credit   || debit    || balance")
  }

  it should "throw an exception if printStream is null" in {
    the[IllegalArgumentException] thrownBy {
      new StatesPrinter(null)
    } should have message "requirement failed: PrintStream cannot be null in StatesPrinter"
  }

}
