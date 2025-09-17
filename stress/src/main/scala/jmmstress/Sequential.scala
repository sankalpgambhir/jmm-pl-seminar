package jmmstress

import base.*

object Sequential extends TestHarness[Tuple6[Int, Int, Int, Int, Int, Int]]:
    class SequentialTest extends StressTest[Tuple6[Int, Int, Int, Int, Int, Int]]:
        // state
        var x: Int = 0

        Test { () =>
            val a0 = x
            x = 1
            val a1 = x
            x = 2
            val a2 = x
            x = 3
            val a3 = x
            x = 4
            val a4 = x
            x = 5
            val a5 = x
            (a0, a1, a2, a3, a4, a5)
        }
    end SequentialTest

    val testCount: Int = 10000

    @main def runSequential =
        val res = test(SequentialTest(), 4)
        println(res)