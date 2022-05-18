package perflist

import model.Predicate

import scala.annotation.tailrec

class FilterQueue[O, T]( val prev: Option[ApplyQueue[O, _, T]], val func: Predicate[T, Boolean] ) extends ApplyQueue[O, T, T]
{
	def apply( elt: O, i: Int ): T = prev match {
		case Some(p) => p.apply( elt, i )
		case None => elt match {
			case (t: T) => t; // reach the end where T = O
			case _ => throw new Exception("Applying element of type " + elt.getClass + " to Queue of types " + this.getClass )
		}
	}
	
	def reduce[V](list: List[O], pred: (V, T) => V, v: V): V = {
		@tailrec
		def rec(l: List[O], i: Int, acc: V ): V = l match {
			case x :: xs =>
				val applied: T = apply(x, i)
				rec( xs, i + 1, func(applied, i) match {
					case true => pred( acc, applied )
					case false => acc
				} )
			case _ => acc;
		}
		rec(list, 0, v);
	}
}
