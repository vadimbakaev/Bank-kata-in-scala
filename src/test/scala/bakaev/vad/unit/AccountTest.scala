package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad.{Account, TransactionStorage, Amount, StatePrinter}
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AccountTest extends FlatSpec with Matchers with MockitoSugar {

  "An account" should "store deposit on state" in {
    val state = mock[TransactionStorage]

    val account = new Account(state)

    account.deposit(new Amount(1000), LocalDate.MIN) should not equal account

    Mockito.verify(state).deposit(new Amount(1000), LocalDate.MIN)
  }

  it should "store deposit on state and return new account" in {
    val state = mock[TransactionStorage]

    val account = new Account(state)

    account.deposit(new Amount(1000), LocalDate.MIN) should not equal account
  }

  it should "store withdraw on state" in {
    val state = mock[TransactionStorage]

    val account = new Account(state)

    account.withdraw(new Amount(1000), LocalDate.MIN)

    Mockito.verify(state).withdraw(new Amount(1000), LocalDate.MIN)
  }

  it should "store withdraw on state and return new account" in {
    val state = mock[TransactionStorage]

    val account = new Account(state)

    account.withdraw(new Amount(1000), LocalDate.MIN) should not equal account
  }

  it should "print statement" in {
    val state = mock[TransactionStorage]
    val statePrinter = mock[StatePrinter]

    val account = new Account(state, statePrinter)

    account.printStatement()

    Mockito.verify(state).printStatements(statePrinter)
  }

}
