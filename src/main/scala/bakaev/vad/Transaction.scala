package bakaev.vad

import java.time.LocalDate

class Transaction(
                   private val localDate: LocalDate,
                   private val operation: Amount,
                   private val balance: Amount
                 ) {

  def printOn(printer: TransactionPrinter): Unit = printer.printLine(localDate, operation, balance)

}
