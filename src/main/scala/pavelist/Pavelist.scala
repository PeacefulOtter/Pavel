package pavelist

import model.Predicate

trait Pavelist[O, T] {
    def add( o: O ): Pavelist[O, T]
    def map[U]( pred: Predicate[T, U] ): Pavelist[O, U];
    def filter( pred: Predicate[T, Boolean] ): Pavelist[O, T];
    def reduce[U]( pred: (U, T) => U, u: U): U;
    def collect(): List[T];

    def print(): Pavelist[O, T] = { println(collect()); this; }
}

case class PavelEmpty[O]( list: List[O] ) extends Pavelist[O, O]
{
    override def add( o: O ): Pavelist[O, O] = PavelEmpty[O]( o :: list )
    override def map[U]( pred: Predicate[O, U] ): Pavelist[O, U] = PavelNonEmpty[O, U]( list, new MapQueue[O, O, U]( None, pred ) )
    override def filter( pred: Predicate[O, Boolean] ): Pavelist[O, O] = PavelNonEmpty[O, O]( list, new FilterQueue[O, O]( None, pred ))
    override def reduce[U]( pred: ( U, O ) => U, u: U ): U = u
    override def collect(): List[O] = list
}

case class PavelNonEmpty[O, T](list: List[O], queue: ApplyQueue[O, _, T] ) extends Pavelist[O, T]
{
    override def add( o: O ): Pavelist[O, T] = PavelNonEmpty[O, T]( o :: list, queue )
    override def map[U]( pred: Predicate[T, U] ): Pavelist[O, U] = PavelNonEmpty( list, queue.mapAdd(pred) )
    override def filter( pred: Predicate[T, Boolean] ): Pavelist[O, T] = PavelNonEmpty( list, queue.filterAdd(pred) )
    override def reduce[U]( pred: (U, T) => U, u: U ): U = queue.reduce( list, pred, u )
    
    // collect is reduce where the predicate is the identity function
    override def collect(): List[T] = reduce( (prev: List[T], cur: T) => prev ::: List(cur), Nil )
}