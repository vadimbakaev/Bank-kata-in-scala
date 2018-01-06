package bakaev.vad.acceptance

import java.io.PrintStream

import bakaev.vad.{Account, Amount, OperationDate}
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AccountPrintStatementTest extends FlatSpec with Matchers with MockitoSugar {

  "An account" should "print statement correctly" in {

    val printStream = mock[PrintStream]
    val account = mock[Account]

    account.deposit(Amount.of(1000), OperationDate.of("10-01-2012"))
    account.deposit(Amount.of(2000), OperationDate.of("13-01-2012"))
    account.withdrawal(Amount.of(500), OperationDate.of("14-01-2012"))

    account.printStatement()

    val orderVerifier = Mockito.inOrder(printStream)

    orderVerifier.verify(printStream).print("date || credit || debit || balance")
    orderVerifier.verify(printStream).print("14/01/2012 || || 500.00 || 2500.00")
    orderVerifier.verify(printStream).print("13/01/2012 || 2000.00 || || 3000.00")
    orderVerifier.verify(printStream).print("10/01/2012 || 1000.00 || || 1000.00")
  }

}
