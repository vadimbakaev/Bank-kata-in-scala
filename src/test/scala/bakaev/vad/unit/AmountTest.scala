package bakaev.vad.unit

import bakaev.vad.domain._
import bakaev.vad.BaseSpec

class AmountTest extends BaseSpec {

  "An amount" should "be equal to itself" in {
    val amount = Amount(100)
    amount should equal(amount)
  }

  it should "be not equal to null" in {
    val amount = Amount(100)
    amount should not equal null
  }

  it should "be equal to amount with same value" in {
    val amount = Amount(5)
    amount should equal(Amount(5))
  }

  it should "be not equal to other object type" in {
    val amount = Amount(100)
    amount should not equal new Object
  }

  it should "be not equal to amount with different value" in {
    val amount = Amount(100)
    amount should not equal Amount(0)
  }

  it should "return negative amount" in {
    val amount = Amount(100)
    -amount should equal(Amount(-100))
  }

  it should "return sum" in {
    val amount = Amount(300) + Amount(200)
    amount should equal(Amount(500))
  }

  it should "print value to PrintStream" in {

    val amount = Amount(777)

    amount.moneyRepresentation should equal("777.00")
  }

  "A ZeroAmount" should "be equals to itself negative" in {
    val amount = Amount(0)

    -amount should equal(ZeroAmount)
  }

  "A NotZeroAmount" should "throw an exception if constructor will be invoked with zero" in {
    the[IllegalArgumentException] thrownBy {
      NotZeroAmount(0)
    } should have message "requirement failed: NotZeroAmount can't be zero"
  }

  "A NegativeAmount" should "on minus return PositiveAmount" in {
    -new NegativeAmount(-7) should equal(new PositiveAmount(7))
  }

  "A PositiveAmount" should "on minus return NegativeAmount" in {
    -new PositiveAmount(77) should equal(new NegativeAmount(-77))
  }
}
