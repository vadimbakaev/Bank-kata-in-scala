package bakaev.vad

import java.io.PrintStream

class Amount(private val value: Int) {

  def print(printStream: PrintStream): Unit = printStream.print(s"$value.00")

  def +(that: Amount): Amount = new Amount(value + that.value)

  def unary_- : Amount = new Amount(-value)

  override def equals(obj: scala.Any): Boolean =
    obj match {
      case that: Amount => value == that.value
      case _            => false
    }

  override def hashCode: Int = value.hashCode()
}
