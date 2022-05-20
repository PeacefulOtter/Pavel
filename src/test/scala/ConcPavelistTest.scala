import org.scalatest.flatspec.AnyFlatSpec
import pavelist.PavelEmpty
import test.benchmark

import scala.collection.parallel.CollectionConverters._

class ConcPavelistTest extends AnyFlatSpec
{
    "Pavelist" should "map and reduce concurrently" in {
        val l: List[Int] = List.range(0, 1000000)
        benchmark(
            l.par.map( _ * 2 ).sum,
            new PavelEmpty(l)
                .conc()
                .map((elt: Int, i: Int) => elt * 2)
                .paraReduce( (acc: Int, cur: Int) => acc + cur, 0, (acc: Int, cur: Int) => acc + cur )
        )
    }

    "Pavelist" should "map filter and reduce concurrently" in {
        val l: List[Int] = List.range(0, 1000000)
        benchmark(
            l.par.map( _ * 2 ).zipWithIndex.filter( elt => elt._2 % 2 == 0).collect( elt => elt._1 ).sum,
            new PavelEmpty(l)
                .conc()
                .map( (elt: Int, i: Int) => elt * 2 )
                .filter( (elt, i) => i % 2 == 0 )
                .reduce( (acc: Int, cur: Int) => acc + cur, 0 )
        )
        /*val res = measure(
            l.map(_ * 2).zipWithIndex.filter( elt => elt._2 % 2 == 0).collect( elt => elt._1 ).sum
        )*/
    }
}
