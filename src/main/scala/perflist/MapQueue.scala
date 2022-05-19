package perflist

import model.Predicate

import scala.annotation.tailrec

class MapQueue[O, T, U]( val prev: Option[ApplyQueue[O, _, T]], val func: Predicate[T, U] ) extends ApplyQueue[O, T, U]
{
	def apply( elt: O, i: Int ): (U, Boolean) = prev match {
		case Some(p) =>
			val (applied, keep): (T, Boolean) = p.apply( elt, i )
			( func(applied, i), keep )
		case None => elt match {
			case (t: T) =>
				// reach the end where T = O
				// .map always keep all elements
				( func(t, i), true );
			case _ => throw new Exception("Applying element of type " + elt.getClass + " to Queue of types " + this.getClass )
		}
	}
	
	def reduce[V](list: List[O], pred: (V, U) => V, v: V): V = {
		@tailrec
		def rec(l: List[O], i: Int, acc: V ): V = l match {
			case x :: xs =>
				val (applied, keep): (U, Boolean) = apply(x, i)
				rec( xs, i + 1, keep match {
					case true => pred( acc, applied )
					case false => acc
				} )
			case _ => acc;
		}
		rec(list, 0, v);
	}
}
