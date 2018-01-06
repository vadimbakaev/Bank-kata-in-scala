package bakaev.vad.unit

import bakaev.vad.Amount
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class AmountTest extends FlatSpec with Matchers with MockitoSugar {

  "An amount" should "be equal to itself" in {
    val result =  Amount(100)
    result should equal(result)
  }

  it should "be not equal to null" in {
    val result =  Amount(100)
    result should not equal null
  }

  it should "be equal to amount with same value" in {
    val result =  Amount(5)
    result should equal( Amount(5))
  }

  it should "be not equal to other object type" in {
    val result =  Amount(100)
    result should not equal new Object
  }

  it should "be not equal to amount with different value" in {
    val result =  Amount(100)
    result should not equal  Amount(0)
  }

  it should "return negative amount" in {
    val result =  Amount(100)
    -result should equal( Amount(-100))
  }

  it should "return sum" in {
    val result =  Amount(300) +  Amount(200)
    result should equal( Amount(500))
  }

  it should "print value to PrintStream" in {

    val result =  Amount(777)

    result.moneyRepresentation should equal("777.00")

  }

  it should "return true in it's less than 0" in {
    val result =  Amount(-1)

    result.isNegative should equal(true)
  }

}
