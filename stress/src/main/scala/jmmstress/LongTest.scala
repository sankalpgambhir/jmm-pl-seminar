package jmmstress

package jmmstress

import base.*

object LongTest extends TestHarness[Long]:
    class LongTestBase extends StressTest[Long]:
        // state
        var a: Long = 0L

        Test { () =>
            a = 70000000000L
            a
        }

        Test { () =>
            a = 80000000000L
            a
        }
    end LongTestBase

    val testCount: Int = 1000000

    @main def runLongTest =
        val res = test(LongTestBase(), 4)
        println(res)