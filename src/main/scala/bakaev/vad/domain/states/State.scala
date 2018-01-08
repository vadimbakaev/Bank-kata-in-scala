package bakaev.vad.domain.states

import bakaev.vad.domain.printers.StateLinePrinter

trait State {
  def printOn(printer: StateLinePrinter): Unit
}
