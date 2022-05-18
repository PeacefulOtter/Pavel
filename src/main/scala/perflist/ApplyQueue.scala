package perflist

import model.Predicate


class ApplyQueue[O, T, U]( prev: Option[ApplyQueue[O, _, T]], func: Predicate[T, U] ) {
	
	def add[V]( elt: Predicate[U, V] ) = new ApplyQueue[O, U, V]( Some(this), elt )
	
	def apply( elt: O, i: Int ): U = prev match {
		case Some(p) => func( p.apply(elt, i ), i )
		case None => elt match {
			case (t: T) => func(t, i);
			case _ => throw new Exception("Applying element of type " + elt.getClass + " to Queue of types " + this.getClass )
		}
	}
}