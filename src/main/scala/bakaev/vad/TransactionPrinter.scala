package bakaev.vad

import java.io.PrintStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter._

import bakaev.vad.TransactionPrinter.Header

class TransactionPrinter(private val printStream: PrintStream) {

  def print(transactions: Seq[Transaction]): Unit = {
    printStream.println(Header)
    transactions.foreach {
      _.printOn(this)
    }
  }

  def printLine(date: LocalDate, operation: Amount, balance: Amount): Unit = {
    val dateString = date.format(ofPattern("dd/MM/uuuu"))

    if (operation.isNegative) {
      printStream.println(s"$dateString ||          || ${(-operation).moneyRepresentation.padTo(9, " ").mkString}|| ${balance.moneyRepresentation}")
    } else {
      printStream.println(s"$dateString || ${operation.moneyRepresentation.padTo(9, " ").mkString}||          || ${balance.moneyRepresentation}")
    }

  }

}

object TransactionPrinter {
  val Header: String = "date       || credit   || debit    || balance"
}
