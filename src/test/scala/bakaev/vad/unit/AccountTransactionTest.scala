package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad.{TransactionStorage, Amount, Transaction}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AccountTransactionTest extends FlatSpec with Matchers with MockitoSugar {

  "An TransactionStorage" should "return new account on deposit" in {
    val state = TransactionStorage()

    val newState = state.deposit(Amount(10), LocalDate.MIN)

    newState should not equal state
  }

  it should "return new account Transaction with new Transaction on deposit" in {
    val seq = mock[Seq[Transaction]]

    when(seq :+ new Transaction(Amount(10), LocalDate.MIN)).thenReturn(Seq(new Transaction(Amount(10), LocalDate.MIN)))

    val state = new TransactionStorage(seq)

    val newState = state.deposit(Amount(10), LocalDate.MIN)

    newState should not equal state

    verify(seq) :+ new Transaction(Amount(10), LocalDate.MIN)
  }

  it should "return new account on withdraw" in {
    val state = TransactionStorage(Nil)

    val newState = state.withdraw(Amount(10), LocalDate.MIN)

    newState should not equal state
  }

  it should "return new account TransactionStorage with new Transaction and negative Amount on withdraw" in {
    val seq = mock[Seq[Transaction]]
    when(seq :+ new Transaction(Amount(-1000), LocalDate.MIN)).thenReturn(Seq(new Transaction(Amount(-1000), LocalDate.MIN)))

    val state = TransactionStorage(seq)

    val newState = state.withdraw(Amount(1000), LocalDate.MIN)

    newState should not equal state

    verify(seq) :+ new Transaction(Amount(-1000), LocalDate.MIN)
  }

  it should "invoke printer with sorted transaction list on printStatement" in {
    val seq = Seq()

    val state = TransactionStorage(seq)

//    state.printStatements()
  }
}
