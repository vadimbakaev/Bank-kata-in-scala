package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad.Amount.{NotZeroAmount, PositiveAmount}
import bakaev.vad.{BaseSpec, Transaction, TransactionStorage}
import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AccountTransactionTest extends BaseSpec {

  "An TransactionStorage" should "return new account on deposit" in {
    val state = TransactionStorage()

    val newState = state.deposit(new PositiveAmount(10), LocalDate.MIN)

    newState should not equal state
  }

  it should "sort transactions on object creation" in {
    val transactions = mock[Seq[Transaction]]

    when(transactions.sorted(ArgumentMatchers.any())).thenReturn(transactions)

    TransactionStorage(transactions)

    verify(transactions).sorted(ArgumentMatchers.any())
  }

  it should "return new account Transaction with new Transaction on deposit" in {
    val transactions = mock[Seq[Transaction]]

    when(transactions.sorted(ArgumentMatchers.any())).thenReturn(transactions)
    when(transactions :+ Transaction(NotZeroAmount(10), LocalDate.MIN)).thenReturn(Seq(Transaction(NotZeroAmount(10), LocalDate.MIN)))

    val state = TransactionStorage(transactions)

    val newState = state.deposit(new PositiveAmount(10), LocalDate.MIN)

    newState should not equal state

    verify(transactions).sorted(ArgumentMatchers.any())
    verify(transactions) :+ Transaction(NotZeroAmount(10), LocalDate.MIN)
  }

  it should "return new account on withdraw" in {
    val state = TransactionStorage(Nil)

    val newState = state.withdraw(new PositiveAmount(10), LocalDate.MIN)

    newState should not equal state
  }

  it should "return new account TransactionStorage with new Transaction and negative Amount on withdraw" in {
    val seq = mock[Seq[Transaction]]

    when(seq.sorted(ArgumentMatchers.any())).thenReturn(seq)
    when(seq :+ Transaction(NotZeroAmount(-1000), LocalDate.MIN)).thenReturn(Seq(Transaction(NotZeroAmount(-1000), LocalDate.MIN)))

    val state = TransactionStorage(seq)

    val newState = state.withdraw(new PositiveAmount(1000), LocalDate.MIN)

    newState should not equal state

    verify(seq).sorted(ArgumentMatchers.any())
    verify(seq) :+ Transaction(NotZeroAmount(-1000), LocalDate.MIN)
  }

  it should "printStatement filtered by date and type" in {

  }

}
