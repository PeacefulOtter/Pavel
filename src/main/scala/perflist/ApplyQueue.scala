package perflist

import model.Predicate

/**
 * @tparam O: Original list type
 * @tparam T: Pred input
 * @tparam U: Pred output
 */
trait ApplyQueue[O, T, U]
{
	def prev: Option[ApplyQueue[O, _, T]]
	// def func: Predicate[T, U]
	
	def apply( elt: O, i: Int ): (U, Boolean) // U = return type, Boolean = keep the element? (in case of filter)
	def reduce[V](list: List[O], pred: (V, U) => V, v: V): V
	
	def mapAdd[V]( elt: Predicate[U, V] ) = new MapQueue[O, U, V]( Some(this), elt )
	def filterAdd( elt: Predicate[U, Boolean] ) = new FilterQueue[O, U]( Some(this), elt )
}