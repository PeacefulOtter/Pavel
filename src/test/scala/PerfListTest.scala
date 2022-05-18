import org.scalatest.flatspec.AnyFlatSpec

class PerfListTest extends AnyFlatSpec {
	"PerfList" should "correctly map a list" in {
		val l: List[Int] = List(1, 2, 3, 4, 5)
		val pl = perflist.PerfEmpty(l)
		  .map( (elt: Int, i: Int) => elt * 2 )
		  .map( (elt: Int, i: Int) => elt.toString )
		  .reduce( (prev: String, cur: String) => prev + cur + " - ", "" )
		println(pl)
	}
}
