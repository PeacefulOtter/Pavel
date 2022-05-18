package perflist

import model.Predicate

import scala.annotation.tailrec

class MapQueue[O, T, U]( val prev: Option[ApplyQueue[O, _, T]], val func: Predicate[T, U] ) extends ApplyQueue[O, T, U]
{
	def apply( elt: O, i: Int ): U = prev match {
		case Some(p) => func( p.apply( elt, i ), i )
		case None => elt match {
			case (t: T) => func(t, i); // reach the end where T = O
			case _ => throw new Exception("Applying element of type " + elt.getClass + " to Queue of types " + this.getClass )
		}
	}
	
	def reduce[V](list: List[O], pred: (V, U) => V, v: V): V = {
		@tailrec
		def rec(l: List[O], i: Int, acc: V ): V = l match {
			case x :: xs => rec( xs, i + 1, pred( acc, apply(x, i) ) )
			case _ => acc;
		}
		rec(list, 0, v);
	}
}
