package pavelist

import model.Predicate

import scala.annotation.tailrec

class FilterQueue[O, T]( val prev: Option[ApplyQueue[O, _, T]], val func: Predicate[T, Boolean] ) extends ApplyQueue[O, T, T]
{
	def apply( elt: O, i: Int ): (T, Boolean) = prev match {
		case Some(p) =>
			val (applied, keep) = p.apply( elt, i )
			(applied, keep && func(applied, i))
		case None => elt match {
			case (t: T) => (t, func(t, i)); // reach the end where T = O, keep depends on the predicate func
			case _ => throw new Exception("Applying element of type " + elt.getClass + " to Queue of types " + this.getClass )
		}
	}
	
	def reduce[V](list: List[O], pred: (V, T) => V, v: V): V = {
		@tailrec
		def rec(l: List[O], i: Int, acc: V ): V = l match {
			case x :: xs =>
				val (applied, keep): (T, Boolean) = apply(x, i)
				rec( xs, i + 1, keep match {
					case true => pred( acc, applied )
					case false => acc
				} )
			case _ => acc;
		}
		rec(list, 0, v);
	}
}
