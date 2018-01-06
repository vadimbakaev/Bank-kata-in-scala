package bakaev.vad.acceptance

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad._
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AccountPrintStatementTest extends FlatSpec with Matchers with MockitoSugar {

  "An account" should "print statement correctly" in {

    val printStream = mock[PrintStream]

    val account = new Account(new TransactionStorage(), new StatePrinter(printStream))
      .deposit(new Amount(1000), LocalDate.of(2012, 1, 10))
      .deposit(new Amount(2000), LocalDate.of(2012, 1, 13))
      .withdraw(new Amount(500), LocalDate.of(2012, 1, 14))

    account.printStatement()

    val orderVerifier = Mockito.inOrder(printStream)

    orderVerifier.verify(printStream).println("date       || credit   || debit    || balance")
    orderVerifier.verify(printStream).println("14/01/2012 ||          || 500.00   || 2500.00")
    orderVerifier.verify(printStream).println("13/01/2012 || 2000.00  ||          || 3000.00")
    orderVerifier.verify(printStream).println("10/01/2012 || 1000.00  ||          || 1000.00")
  }

}
