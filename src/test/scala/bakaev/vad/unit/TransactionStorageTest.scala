package bakaev.vad.unit

import java.time.LocalDate

import bakaev.vad.{StatePrinter, TransactionStorage}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class TransactionStorageTest extends FlatSpec with Matchers with MockitoSugar {

  "A TransactionStorage" should "invoke statePrinter with from to dates on printStatements" in {
    val printer = mock[StatePrinter]

    val storage = TransactionStorage()
    val from = LocalDate.MIN
    val to = LocalDate.MAX

    doNothing().when(printer).print(from, to, Nil)

    storage.printStatements(from, to, printer)

    verify(printer).print(from , to, Nil)
  }

}
