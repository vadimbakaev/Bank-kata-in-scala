package bakaev.vad.unit

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad.Amount.PositiveAmount
import bakaev.vad._
import org.mockito.Mockito
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class StatePrinterTest extends FlatSpec with Matchers with MockitoSugar {

  "A StatePrinter" should "print only header for empty list" in {
    val printStream = mock[PrintStream]
    val printer = new StatePrinter(printStream)

    val from = LocalDate.MIN
    val to = LocalDate.MAX

    printer.print(from, to, Nil)

    verify(printStream).println("date       || credit   || debit    || balance")
  }

  it should "print header and each state print on itself" in {
    val printStream = mock[PrintStream]
    val printer = new StatePrinter(printStream)

    val state1 = mock[State]
    val state2 = mock[State]
    val state3 = mock[State]

    val states = Seq(state1, state2, state3)

    val from = LocalDate.MIN
    val to = LocalDate.MAX

    printer.print(from, to, states)

    val orderVerifier = Mockito.inOrder(state1, state2, state3)

    orderVerifier.verify(state1).printMatchedOn(from, to, printer)
    orderVerifier.verify(state2).printMatchedOn(from, to, printer)
    orderVerifier.verify(state3).printMatchedOn(from, to, printer)
  }

  it should "print state line" in {
    val printStream = mock[PrintStream]
    val printer = new StatePrinter(printStream)

    printer.printLine(LocalDate.of(2012, 1, 10), new PositiveAmount(1000), Amount(1000))

    verify(printStream).println("10/01/2012 || 1000.00  ||          || 1000.00")
  }
}
