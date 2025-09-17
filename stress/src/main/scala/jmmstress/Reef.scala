package jmmstress

package jmmstress

import base.*

object Reef extends TestHarness[Tuple2[Int, Int]]:
    class ReefBase extends StressTest[Tuple2[Int, Int]]:
        // state
        @volatile var greatBarrierReef: Int = 0
        var x: Int = 0
        var y: Int = 0

        Test { () =>
            x = 1
            y = 1
            greatBarrierReef = 1
            (0, 0)
        }

        Test { () =>
            greatBarrierReef = 2
            val a = y
            val b = x
            (b, a)
        }
    end ReefBase

    val testCount: Int = 1000000

    @main def runReef =
        val res = test(ReefBase(), 4)
        println(res)