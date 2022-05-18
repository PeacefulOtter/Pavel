import org.scalatest.flatspec.AnyFlatSpec
import perflist.PerfEmpty

class PerfListTest extends AnyFlatSpec {
	"PerfList" should "correctly map and reduce a list" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PerfEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 2 )
		  .reduce( (prev: Int, cur: Int) => prev + cur, 0 )
		println(pl)
	}
	
	"PerfList" should "map collect" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PerfEmpty(l)
		  	.map( (elt: Int, i: Int) => elt * 2 )
		    .collect()
		println(pl)
	}
	
	"PerfList" should "filter collect" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PerfEmpty(l)
		  .filter( (elt: Int, i: Int) => elt % 2 == 0)
		  .collect()
		println(pl)
	}
	
	"PerfList" should "map filter map" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = PerfEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 3 )
		  .filter( (elt: Int, i: Int) => (elt % 2) == 0)
		  // .map( (elt: Int, i: Int) => elt / 2 )  -> breaks
		  .collect()
		println(pl)
	}
}
