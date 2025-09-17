package jmmstress

import base.*

object NonSC extends TestHarness[Int]:
    class NonSCTest extends StressTest[Int]:
        // state
        var a: Boolean = false
        var b: Boolean = false

        Test { () =>
            a = true
            if b then 
                0
            else
                1
        }

        Test { () =>
            b = true
            if a then 
                0
            else
                1
        }
    end NonSCTest

    val testCount: Int = 1000000

    @main def runNonSC =
        val res = test(NonSCTest(), 4)
        println(res)