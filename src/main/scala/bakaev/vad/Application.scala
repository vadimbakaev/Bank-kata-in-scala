package bakaev.vad

import java.time.LocalDate

import bakaev.vad.domain.{Account, PositiveAmount}

object Application extends App {

  val fstAccount = new Account()
    .deposit(new PositiveAmount(12207), LocalDate.of(2012, 1, 11))
    .deposit(new PositiveAmount(12456), LocalDate.of(2013, 3, 22))
    .withdraw(new PositiveAmount(1900), LocalDate.of(2014, 5, 9))
    .deposit(new PositiveAmount(12323), LocalDate.of(1999, 2, 21))
    .withdraw(new PositiveAmount(1900), LocalDate.of(2016, 12, 31))

  val sndAccount = new Account()

  val (updatedFstAccount, updatedSndAccount) =
    fstAccount.transfer(new PositiveAmount(400), LocalDate.now(), sndAccount)

  println("First account ALL statement:")
  updatedFstAccount.printStatement()
  println()
  println("First account CREDIT statement before 2000:")
  updatedFstAccount.printStatement(before = LocalDate.of(2000, 1, 1), canPrint = CREDIT)
  println()
  println("Second account ALL statement:")
  updatedSndAccount.printStatement()
}
