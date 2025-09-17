package jmmstress.base

import scala.collection.mutable.{Set as MSet}
import util.Random.shuffle

trait TestHarness[A]:
    val testCount: Int

    class ResThread[A](f: => A) extends Thread:
        var result: A = _
        override def run(): Unit = 
            result = f

    def joinMap[A](l: Map[A, Int], r: Map[A, Int]): Map[A, Int] =
        val keys = l.keySet ++ r.keySet
        keys.map(k => k -> (l.getOrElse(k, 0) + r.getOrElse(k, 0))).toMap

    def testSingle[S <: StressTest[A]](generate: => S, count: Int): Map[Seq[A], Int] =
        (1 to count).map(i => generate.runTests)
                        .groupBy(identity)
                        .map((k, v) => k -> v.length)

    def test[S <: StressTest[A]](generate: => S, parCount: Int = 1): Map[Seq[A], Int] =
        val threads = (1 to parCount).map(i => ResThread(testSingle(generate, testCount/parCount)))
        threads.foreach(_.start())
        threads.foreach(_.join())

        threads.map(_.result).reduce(joinMap(_, _))


trait StressTest[A]:
    val tests: MSet[Test] = MSet.empty[Test]

    class Test(val f: () => A) extends scala.annotation.StaticAnnotation:
        tests += this

    class ResThread(t: Test) extends Thread:
        var result: A = _
        override def run =
            result = t.f()

    def runTests: Seq[A] =
        val threads = tests.toSeq.map(ResThread(_))
        shuffle(threads).foreach(_.start())
        threads.foreach(_.join())
        threads.map(_.result)
    
end StressTest

