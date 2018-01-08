package bakaev

package object vad {

  sealed trait Operation

  case object ALL extends Operation

  case object CREDIT extends Operation

  case object DEBIT extends Operation

}
