package jmmstress

import base.*
import java.util.concurrent.atomic.AtomicInteger

object Simple extends TestHarness[Int]:
    
    class SimpleTest extends StressTest[Int]:
        // state
        val v = AtomicInteger(0)
        
        // reset
        def reset =
            v.set(0)

        // define actors
        Test { () =>
            v.incrementAndGet()
        }

        Test { () =>
            v.incrementAndGet()
        }

    end SimpleTest

    val testCount: Int = 100

    @main def runSimple = 
        val res = test(SimpleTest(), 4)
        println(res)
