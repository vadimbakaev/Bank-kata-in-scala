package bakaev.vad

import org.scalatest.{FlatSpec, Matchers, ParallelTestExecution}
import org.scalatest.mockito.MockitoSugar

trait BaseSpec extends FlatSpec with Matchers with MockitoSugar with ParallelTestExecution
