package bakaev.vad.unit

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad._
import org.mockito.Mockito
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class StatePrinterTest extends FlatSpec with Matchers with MockitoSugar {

  "A StatePrinter" should "print only header for empty list" in {
    val printStream = mock[PrintStream]
    val printer = new StatePrinter(printStream)

    printer.print(Nil)

    verify(printStream).println("date       || credit   || debit    || balance")
  }

  it should "print header and each state print on itself" in {
    val printStream = mock[PrintStream]
    val printer = new StatePrinter(printStream)

    val state1 = mock[State]
    val state2 = mock[State]
    val state3 = mock[State]

    val states = Seq(state1, state2, state3)

    printer.print(states)

    val orderVerifier = Mockito.inOrder(state1, state2, state3)

    orderVerifier.verify(state1).printOn(printer)
    orderVerifier.verify(state2).printOn(printer)
    orderVerifier.verify(state3).printOn(printer)
  }

  it should "print state line" in {
    val printStream = mock[PrintStream]
    val printer = new StatePrinter(printStream)

    printer.printLine(LocalDate.of(2012, 1, 10), new Amount(1000), new Amount(1000))

    verify(printStream).println("10/01/2012 || 1000.00  ||          || 1000.00")
  }
}
