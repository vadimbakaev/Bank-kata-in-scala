package bakaev.vad.acceptance

import java.io.PrintStream
import java.time.LocalDate

import bakaev.vad.printers.StatesPrinter
import bakaev.vad.{BaseSpec, _}
import org.mockito.Mockito

class AccountPrintStatementTest extends BaseSpec {

  "An account" should "print statement correctly" in {

    val printStream = mock[PrintStream]

    val account = new Account(TransactionStorage(), new StatesPrinter(printStream))
      .deposit(new PositiveAmount(1001), LocalDate.of(2012, 1, 1))
      .withdraw(new PositiveAmount(1000), LocalDate.of(2012, 1, 2))
      .deposit(new PositiveAmount(1000), LocalDate.of(2012, 1, 10))
      .withdraw(new PositiveAmount(500), LocalDate.of(2012, 1, 14))
      .withdraw(new PositiveAmount(2500), LocalDate.of(2014, 12, 31))
      .deposit(new PositiveAmount(2000), LocalDate.of(2012, 1, 13))

    account.printStatement(LocalDate.of(2012, 1, 9), LocalDate.of(2013, 1, 11), ALL)

    val orderVerifier = Mockito.inOrder(printStream)

    orderVerifier.verify(printStream).println("date       || credit   || debit    || balance")
    orderVerifier.verify(printStream).println("14/01/2012 ||          || 500.00   || 2501.00")
    orderVerifier.verify(printStream).println("13/01/2012 || 2000.00  ||          || 3001.00")
    orderVerifier.verify(printStream).println("10/01/2012 || 1000.00  ||          || 1001.00")
  }

  it should "print only DEBIT" in {

    val printStream = mock[PrintStream]

    val account = new Account(TransactionStorage(), new StatesPrinter(printStream))
      .deposit(new PositiveAmount(1001), LocalDate.of(2012, 1, 1))
      .withdraw(new PositiveAmount(1000), LocalDate.of(2012, 1, 2))
      .deposit(new PositiveAmount(1000), LocalDate.of(2012, 1, 10))
      .withdraw(new PositiveAmount(500), LocalDate.of(2012, 1, 14))
      .withdraw(new PositiveAmount(2500), LocalDate.of(2014, 12, 31))
      .deposit(new PositiveAmount(2000), LocalDate.of(2012, 1, 13))

    account.printStatement(LocalDate.of(2012, 1, 9), LocalDate.of(2013, 1, 11), DEBIT)

    val orderVerifier = Mockito.inOrder(printStream)

    orderVerifier.verify(printStream).println("date       || credit   || debit    || balance")
    orderVerifier.verify(printStream).println("14/01/2012 ||          || 500.00   || 2501.00")
  }

}
