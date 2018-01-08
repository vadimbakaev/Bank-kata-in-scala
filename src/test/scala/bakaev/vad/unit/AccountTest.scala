package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad.Transaction.{Credit, Debit}
import bakaev.vad._
import bakaev.vad.filters.{DateFilter, OperationFilter}
import bakaev.vad.printers.StatesPrinter
import org.mockito.Mockito._

class AccountTest extends BaseSpec {

  "An account" should "store deposit on state" in {
    val state = mock[TransactionStorage]
    val statePrinter = mock[StatesPrinter]

    val account = new Account(state, statePrinter)

    when(state.deposit(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())

    account.deposit(new PositiveAmount(1000), LocalDate.MIN) should not equal account

    verify(state).deposit(new PositiveAmount(1000), LocalDate.MIN)
  }

  it should "store deposit on state and return new account" in {
    val state = mock[TransactionStorage]
    val statePrinter = mock[StatesPrinter]

    val account = new Account(state, statePrinter)

    when(state.deposit(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())
    account.deposit(new PositiveAmount(1000), LocalDate.MIN) should not equal account
  }

  it should "store withdraw on state" in {
    val state = mock[TransactionStorage]
    val statePrinter = mock[StatesPrinter]

    val account = new Account(state, statePrinter)

    when(state.withdraw(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())
    account.withdraw(new PositiveAmount(1000), LocalDate.MIN)

    verify(state).withdraw(new PositiveAmount(1000), LocalDate.MIN)
  }

  it should "store withdraw on state and return new account" in {
    val state = mock[TransactionStorage]
    val statePrinter = mock[StatesPrinter]

    val account = new Account(state, statePrinter)
    when(state.withdraw(new PositiveAmount(1000), LocalDate.MIN)).thenReturn(TransactionStorage())

    account.withdraw(new PositiveAmount(1000), LocalDate.MIN) should not equal account
  }

  it should "transfer amount to an account, return accounts updated with transactions" in {
    val statePrinter = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]
    val sndAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statePrinter)
    val sndAccount = new Account(sndAccountState, statePrinter)

    val fstUpdatedTransactionStorage = TransactionStorage(Seq(new Debit(new NegativeAmount(-100), LocalDate.MIN)))
    val sndUpdatedTransactionStorage = TransactionStorage(Seq(new Credit(new PositiveAmount(100), LocalDate.MIN)))

    when(fstAccountState.withdraw(new PositiveAmount(100), LocalDate.MIN)).thenReturn(fstUpdatedTransactionStorage)
    when(sndAccountState.deposit(new PositiveAmount(100), LocalDate.MIN)).thenReturn(sndUpdatedTransactionStorage)

    val (fstUpdatedAccount, sndUpdatedAccount) = fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, sndAccount)

    fstUpdatedAccount should equal (new Account(fstUpdatedTransactionStorage, statePrinter))
    sndUpdatedAccount should equal (new Account(sndUpdatedTransactionStorage, statePrinter))
  }

  it should "throw an exception if receiver is null" in {
    val statePrinter = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statePrinter)

    the[IllegalArgumentException] thrownBy {
      fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, null)
    } should have message "requirement failed: Receiver cannot be null in transfer"
  }

  it should "throw an exception if sender is receiver" in {
    val statePrinter = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statePrinter)

    the[IllegalArgumentException] thrownBy {
      fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, fstAccount)
    } should have message "requirement failed: Receiver cannot be sender in transfer"
  }

  it should "execute transfer if sender and reciever are equals" in {
    val statePrinter = mock[StatesPrinter]
    val fstAccountState = mock[TransactionStorage]

    val fstAccount = new Account(fstAccountState, statePrinter)
    val sndAccount = new Account(fstAccountState, statePrinter)

    when(fstAccountState.withdraw(new PositiveAmount(100), LocalDate.MIN)).thenReturn(TransactionStorage())
    when(fstAccountState.deposit(new PositiveAmount(100), LocalDate.MIN)).thenReturn(TransactionStorage())

    fstAccount.transfer(new PositiveAmount(100), LocalDate.MIN, sndAccount)

    verify(fstAccountState).withdraw(new PositiveAmount(100), LocalDate.MIN)
    verify(fstAccountState).deposit(new PositiveAmount(100), LocalDate.MIN)
  }

  it should "printStatement between two dates" in {
    val state = mock[TransactionStorage]
    val statePrinter = mock[StatesPrinter]

    val account = new Account(state, statePrinter)
    val from = LocalDate.MIN
    val to = LocalDate.MAX

    account.printStatement(from, to, ALL)

    verify(state).printStatements(new DateFilter(from, to), new OperationFilter(ALL), statePrinter)
  }

}
