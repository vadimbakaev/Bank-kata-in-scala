package bakaev.vad

import java.io.PrintStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter._

import bakaev.vad.Amount.{NegativeAmount, PositiveAmount}
import bakaev.vad.StatePrinter._

class StatePrinter(private val printStream: PrintStream) {

  def print(states: Seq[State]): Unit = {
    printStream.println(Header)
    states.foreach {
      _.printOn(this)
    }
  }

  def printLine(date: LocalDate, debit: NegativeAmount, balance: Amount): Unit = {
    val dateString = date.format(ofPattern(DatePattern))

    val debitRepresentation = debit.moneyAbsRepresentation.padTo(9, " ").mkString
    printStream.println(
      s"$dateString || $EmptyBlock|| $debitRepresentation|| ${balance.moneyRepresentation}"
    )
  }

  def printLine(date: LocalDate, credit: PositiveAmount, balance: Amount): Unit = {
    val dateString           = date.format(ofPattern(DatePattern))
    val creditRepresentation = credit.moneyAbsRepresentation.padTo(9, " ").mkString
    printStream.println(
      s"$dateString || $creditRepresentation|| $EmptyBlock|| ${balance.moneyRepresentation}"
    )
  }

}

object StatePrinter {
  val Header: String      = "date       || credit   || debit    || balance"
  val DatePattern: String = "dd/MM/uuuu"
  val EmptyBlock: String  = "         "
}
