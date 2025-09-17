package jmmstress

package jmmstress

import base.*

object Barriers extends TestHarness[Tuple2[Int, Int]]:
    class BarriersBase extends StressTest[Tuple2[Int, Int]]:
        // state
        var x: Int = 0
        var y: Int = 0

        Test { () =>
            synchronized {x = 1}
            synchronized {y = 1}
            (x, y)
        }

        Test { () =>
            val a = y
            val b = x
            (b, a)
        }
    end BarriersBase

    val testCount: Int = 1000000

    @main def runBarriers =
        val res = test(BarriersBase(), 4)
        println(res)