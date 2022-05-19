import org.scalatest.flatspec.AnyFlatSpec
import pavelist.PavelEmpty

class PavelistTest extends AnyFlatSpec {
	"Pavelist" should "correctly map and reduce a list" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = new PavelEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 2 )
		  .reduce( (prev: Int, cur: Int) => prev + cur, 0 )
		assert( pl == 30 )
	}
	
	"Pavelist" should "map collect" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = new PavelEmpty(l)
		  	.map( (elt: Int, i: Int) => elt * 2 )
		    .collect()
		assert( pl == List(2, 4, 6, 8, 10) )
	}
	
	"Pavelist" should "filter collect" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = new PavelEmpty(l)
		  .filter( (elt: Int, i: Int) => elt % 2 == 0)
		  .collect()
		assert( pl == List(2, 4) )
	}

	"Pavelist" should "print after maps and filters" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = new PavelEmpty(l)
			.print()
			.map( (elt: Int, i: Int) => elt * 3 )
			.print()
			.filter( (elt: Int, i: Int) => (elt % 2) == 0)
			.print()
			.map( (elt: Int, i: Int) => elt / 2 )
			.print()
			.collect()
		assert( pl == List(3, 6) )
	}
	
	"Pavelist" should "map filter map" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = new PavelEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 3 )
		  .filter( (elt: Int, i: Int) => (elt % 2) == 0)
		  .map( (elt: Int, i: Int) => elt / 2 )
		  .collect()
		assert( pl == List(3, 6) )
	}

	"Pavelist" should "map concurrently" in {
		val l: List[Int] = List.range(0, 1000000)
		val pl = new PavelEmpty(l)
			.map( (elt: Int, i: Int) => elt * 2 )
			.conc()
			.reduce( (acc: Int, cur: Int) => acc + cur, 0 )
		assert( pl == l.map(_ * 2).sum )
	}
}
