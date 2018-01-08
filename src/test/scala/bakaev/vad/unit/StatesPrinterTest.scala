package bakaev.vad.unit

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad._
import bakaev.vad.filters.{DateFilter, OperationFilter}
import bakaev.vad.printers.StatesPrinter
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

}
