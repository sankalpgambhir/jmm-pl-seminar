package jmmstress

package jmmstress

import base.*

object IRIW extends TestHarness[Tuple2[Int, Int]]:
    class IRIWBase extends StressTest[Tuple2[Int, Int]]:
        // state
        var x: Int = 0
        var y: Int = 0

        Test { () =>
            x = 1
            (0, 0)
        }

        Test { () =>
            y = 1
            (0, 0)
        }

        Test { () =>
            val a = x
            val b = y
            (a, b)
        }

        Test { () =>
            val b = y
            val a = x
            (a, b)
        }
    end IRIWBase

    val testCount: Int = 1000000

    @main def runIRIW =
        val res = test(IRIWBase(), 2)
        println(res)