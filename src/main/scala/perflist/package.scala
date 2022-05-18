
package object model {
	type Predicate[U, V] = (U, Int) => V
	type BoolPredicate[U] = Predicate[U, Boolean]
}
