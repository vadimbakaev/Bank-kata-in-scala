package bakaev.vad.unit

import java.io.PrintStream

import bakaev.vad.Amount
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AmountTest extends FlatSpec with Matchers with MockitoSugar {

  "An amount" should "be equal to itself" in {
    val result = new Amount(100)
    result should equal(result)
  }

  it should "be not equal to null" in {
    val result = new Amount(100)
    result should not equal null
  }

  it should "be equal to amount with same value" in {
    val result = new Amount(5)
    result should equal(new Amount(5))
  }

  it should "be not equal to other object type" in {
    val result = new Amount(100)
    result should not equal new Object
  }

  it should "be not equal to amount with different value" in {
    val result = new Amount(100)
    result should not equal new Amount(0)
  }

  it should "return negative amount" in {
    val result = new Amount(100)
    -result should equal(new Amount(-100))
  }

  it should "return sum" in {
    val result = new Amount(300) + new Amount(200)
    result should equal(new Amount(500))
  }

  it should "print value to PrintStream" in {
    val printStream = mock[PrintStream]

    val result = new Amount(777)

    result.print(printStream)

    verify(printStream).print("777.00")
  }

}
