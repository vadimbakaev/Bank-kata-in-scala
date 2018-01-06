package bakaev.vad.unit

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad._
import org.mockito.Mockito
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TransactionPrinterTest extends FlatSpec with Matchers with MockitoSugar {

  "A StatePrinter" should "print only header for empty list" in {
    val printStream = mock[PrintStream]
    val printer = new TransactionPrinter(printStream)

    printer.print(Nil)

    verify(printStream).println("date       || credit   || debit    || balance")
  }

  it should "print header and each transaction print on itself" in {
    val printStream = mock[PrintStream]
    val printer = new TransactionPrinter(printStream)

    val transaction1 = mock[Transaction]
    val transaction2 = mock[Transaction]
    val transaction3 = mock[Transaction]

    val transactions = Seq(transaction1, transaction2, transaction3)

    printer.print(transactions)

    val orderVerifier = Mockito.inOrder(transaction1, transaction2, transaction3)

    orderVerifier.verify(transaction1).printOn(printer)
    orderVerifier.verify(transaction2).printOn(printer)
    orderVerifier.verify(transaction3).printOn(printer)
  }

  it should "print transaction line" in {
    val printStream = mock[PrintStream]
    val printer = new TransactionPrinter(printStream)

    printer.printLine(LocalDate.of(2012, 1, 10), new Amount(1000), new Amount(1000))

    verify(printStream).println("10/01/2012 || 1000.00  ||          || 1000.00")
  }
}
