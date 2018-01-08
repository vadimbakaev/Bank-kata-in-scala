package bakaev.vad.states

import bakaev.vad.printers.StateLinePrinter

trait State {
  def printOn(printer: StateLinePrinter): Unit
}
