import org.scalatest.flatspec.AnyFlatSpec
import pavelist.PavelEmpty

class PavelistTest extends AnyFlatSpec {
	"Pavelist" should "correctly map and reduce a list" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PavelEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 2 )
		  .reduce( (prev: Int, cur: Int) => prev + cur, 0 )
		println(pl)
	}
	
	"Pavelist" should "map collect" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PavelEmpty(l)
		  	.map( (elt: Int, i: Int) => elt * 2 )
		    .collect()
		println(pl)
	}
	
	"Pavelist" should "filter collect" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PavelEmpty(l)
		  .filter( (elt: Int, i: Int) => elt % 2 == 0)
		  .collect()
		println(pl)
	}

	"Pavelist" should "print after maps and filters" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PavelEmpty(l)
			.print()
			.map( (elt: Int, i: Int) => elt * 3 )
			.print()
			.filter( (elt: Int, i: Int) => (elt % 2) == 0)
			.print()
			.map( (elt: Int, i: Int) => elt / 2 )
			.print()
			.collect()
		println(pl)
	}
	
	"Pavelist" should "map filter map" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PavelEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 3 )
		  .filter( (elt: Int, i: Int) => (elt % 2) == 0)
		  .map( (elt: Int, i: Int) => elt / 2 )
		  .collect()
		println(pl)
	}
}
