import org.scalameter.{Key, MeasureBuilder, Warmer, config}

package object test {
    val standardConfig: MeasureBuilder[Unit, Double] = config(
        Key.exec.minWarmupRuns := 40,
        Key.exec.maxWarmupRuns := 80,
        Key.exec.benchRuns := 30,
        Key.verbose := false
    ) withWarmer(new Warmer.Default)

    def time[T](f: => T): T = {
        val start = System.nanoTime()
        val ret = f
        val end = System.nanoTime()
        println(s"Time taken: ${(end - start) / 1000 / 1000} ms")
        ret
    }

    def measure[T](f: => T): T = {
        val (res, time) = standardConfig measured { f }
        println(time)
        res
    }

    def benchmark[T](f: => T, g: => T): Unit = {
        measure(f)
        measure(g)
        val a = measure(g)
        val b =  measure(f)
        assert( a == b )
    }
}