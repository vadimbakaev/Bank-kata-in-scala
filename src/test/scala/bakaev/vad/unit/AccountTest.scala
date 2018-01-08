package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad._
import bakaev.vad.domain.Transaction.{Credit, Debit}
import bakaev.vad.domain.filters.{DateFilter, OperationFilter}
import bakaev.vad.domain.printers.StatesPrinter
import bakaev.vad.domain.{Account, NegativeAmount, PositiveAmount, TransactionStorage}
import org.mockito.Mockito._

class AccountTest extends BaseSpec {

  "An account" should "store deposit on state" in {
    val state         = mock[TransactionStorage]
    val statesPrinter = mock[StatesPrinter]

    val account = new Account(state, statesPrinter)

    when(state.deposit(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())

    account.deposit(new PositiveAmount(1000), LocalDate.MIN) should not equal account

    verify(state).deposit(new PositiveAmount(1000), LocalDate.MIN)
  }

  it should "throw an exception if statesPrinter is null" in {
    val statesPrinter = null
    val state         = mock[TransactionStorage]

    the[IllegalArgumentException] thrownBy {
      new Account(state, statesPrinter)
    } should have message "requirement failed: StatesPrinter cannot be null in Account"
  }

  it should "throw an exception if TransactionStorage is null" in {
    val statesPrinter = mock[StatesPrinter]
    val state         = null

    the[IllegalArgumentException] thrownBy {
      new Account(state, statesPrinter)
    } should have message "requirement failed: TransactionStorage cannot be null in Account"
  }

  it should "throw an exception if deposit amount is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().deposit(null, LocalDate.MAX)
    } should have message "requirement failed: Amount cannot be null in deposit"
  }

  it should "throw an exception if deposit date is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().deposit(new PositiveAmount(100), null)
    } should have message "requirement failed: Date cannot be null in deposit"
  }

  it should "throw an exception if deposit withdraw is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().withdraw(null, LocalDate.MAX)
    } should have message "requirement failed: Amount cannot be null in withdraw"
  }

  it should "throw an exception if withdraw date is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().withdraw(new PositiveAmount(100), null)
    } should have message "requirement failed: Date cannot be null in withdraw"
  }

  it should "throw an exception if transfer date is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().transfer(new PositiveAmount(100), null, new Account())
    } should have message "requirement failed: Date cannot be null in transfer"
  }

  it should "throw an exception if transfer amount is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().transfer(null, LocalDate.MAX, new Account())
    } should have message "requirement failed: Amount cannot be null in transfer"
  }

  it should "throw an exception if printStatement after is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().printStatement(after = null)
    } should have message "requirement failed: After cannot be null in printStatement"
  }

  it should "throw an exception if printStatement before is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().printStatement(before = null)
    } should have message "requirement failed: Before cannot be null in printStatement"
  }

  it should "throw an exception if printStatement canPrint is null" in {
    the[IllegalArgumentException] thrownBy {
      new Account().printStatement(canPrint = null)
    } should have message "requirement failed: CanPrint cannot be null in printStatement"
  }

  it should "store deposit on state and return new account" in {
    val state         = mock[TransactionStorage]
    val statesPrinter = mock[StatesPrinter]

    val account = new Account(state, statesPrinter)

    when(state.deposit(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())
    account.deposit(new PositiveAmount(1000), LocalDate.MIN) should not equal account
  }

  it should "store withdraw on state" in {
    val state         = mock[TransactionStorage]
    val statesPrinter = mock[StatesPrinter]

    val account = new Account(state, statesPrinter)

    when(state.withdraw(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())
    account.withdraw(new PositiveAmount(1000), LocalDate.MIN)

    verify(state).withdraw(new PositiveAmount(1000), LocalDate.MIN)
  }

  it should "store withdraw on state and return new account" in {
    val state         = mock[TransactionStorage]
    val statesPrinter = mock[StatesPrinter]

    val account = new Account(state, statesPrinter)
    when(state.withdraw(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())

    account.withdraw(new PositiveAmount(1000), LocalDate.MIN) should not equal account
  }

  it should "transfer amount to an account, return accounts updated with transactions" in {
    val statesPrinter   = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]
    val sndAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statesPrinter)
    val sndAccount = new Account(sndAccountState, statesPrinter)

    val fstUpdatedTransactionStorage = TransactionStorage(Seq(new Debit(new NegativeAmount(-100), LocalDate.MIN)))
    val sndUpdatedTransactionStorage = TransactionStorage(Seq(new Credit(new PositiveAmount(100), LocalDate.MIN)))

    when(fstAccountState.withdraw(new PositiveAmount(100), LocalDate.MIN)).thenReturn(fstUpdatedTransactionStorage)
    when(sndAccountState.deposit(new PositiveAmount(100), LocalDate.MIN)).thenReturn(sndUpdatedTransactionStorage)

    val (fstUpdatedAccount, sndUpdatedAccount) = fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, sndAccount)

    fstUpdatedAccount should equal(new Account(fstUpdatedTransactionStorage, statesPrinter))
    sndUpdatedAccount should equal(new Account(sndUpdatedTransactionStorage, statesPrinter))
  }

  it should "throw an exception if receiver is null" in {
    val statesPrinter   = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statesPrinter)

    the[IllegalArgumentException] thrownBy {
      fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, null)
    } should have message "requirement failed: Receiver cannot be null in transfer"
  }

  it should "throw an exception if sender is receiver" in {
    val statesPrinter   = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statesPrinter)

    the[IllegalArgumentException] thrownBy {
      fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, fstAccount)
    } should have message "requirement failed: Receiver cannot be sender in transfer"
  }

  it should "execute transfer if sender and receiver are equals" in {
    val statesPrinter   = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statesPrinter)
    val sndAccount = new Account(fstAccountState, statesPrinter)

    when(fstAccountState.withdraw(new PositiveAmount(100), LocalDate.MIN)).thenReturn(TransactionStorage())
    when(fstAccountState.deposit(new PositiveAmount(100), LocalDate.MIN)).thenReturn(TransactionStorage())

    fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, sndAccount)

    verify(fstAccountState).withdraw(new PositiveAmount(100), LocalDate.MIN)
    verify(fstAccountState).deposit(new PositiveAmount(100), LocalDate.MIN)
  }

  it should "printStatement between two dates" in {
    val state         = mock[TransactionStorage]
    val statesPrinter = mock[StatesPrinter]

    val account = new Account(state, statesPrinter)
    val from    = LocalDate.MIN
    val to      = LocalDate.MAX

    account.printStatement(from, to, ALL)

    verify(state).printStatements(new DateFilter(from, to), new OperationFilter(ALL), statesPrinter)
  }

}
