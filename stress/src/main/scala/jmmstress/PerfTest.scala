package jmmstress

object PerfTest:
    def unsynced =
        @volatile var y = 0
        var x: Integer = 0
        val t1 = new Thread {
            override def run(): Unit =
                x = x + 1
        }
        val t2 = new Thread {
            override def run(): Unit =
                x = x + 1
        }
        val t3 = new Thread {
            override def run(): Unit =
                x = x + 1
                y = x
        }
        val threads = Seq(t1, t2, t3)
        threads.foreach(_.start())
        threads.foreach(_.join())
        y
    
    def synced =
        @volatile var y = 0
        var x: Integer = 0
        val t1 = new Thread {
            override def run(): Unit =
                (1 to 100).foreach(i => x.synchronized {x = x + 1})
        }
        val t2 = new Thread {
            override def run(): Unit =
                (1 to 100).foreach(i => x.synchronized {x = x + 1})
        }
        val t3 = new Thread {
            override def run(): Unit =
                (1 to 100).foreach(i => x.synchronized {x = x + 1})
                x.synchronized {y = x}
        }
        val threads = Seq(t1, t2, t3)
        threads.foreach(_.start())
        threads.foreach(_.join())
        y

    def bench[A](f: () => A) =
        val t1 = System.currentTimeMillis()
        (1 to 100).foreach(i => f())
        val t2 = System.currentTimeMillis()
        t2 - t1

    @main def test =
        val total = 100
        val unsync = (1 to total).map(i => bench(() => unsynced)).sum / total
        val sync = (1 to total).map(i => bench(() => synced)).sum / total
        println(s"Synced $sync ms")
        println(s"Unsynced $unsync ms")
