package pavelist

import model.Predicate

trait Pavelist[O, T]
{
    def list: List[O]

    def add( o: O ): Pavelist[O, T]
    def map[U]( pred: Predicate[T, U] ): Pavelist[O, U];
    def filter( pred: Predicate[T, Boolean] ): Pavelist[O, T];
    def reduce[U]( pred: (U, T) => U, u: U): U;
    def collect(): List[T];
    def conc(): ConcPavelist[O, T]

    def print(): Pavelist[O, T] = { println(collect()); this; }
}

class PavelEmpty[O]( val list: List[O] ) extends Pavelist[O, O]
{
    override def add( o: O ): Pavelist[O, O] = new PavelEmpty[O]( o :: list )
    override def map[U]( pred: Predicate[O, U] ): Pavelist[O, U] = new PavelNonEmpty[O, U]( list, new MapQueue[O, O, U]( None, pred ) )
    override def filter( pred: Predicate[O, Boolean] ): Pavelist[O, O] = new PavelNonEmpty[O, O]( list, new FilterQueue[O, O]( None, pred ))
    override def reduce[U]( pred: ( U, O ) => U, u: U ): U = u
    override def collect(): List[O] = list
    override def conc(): ConcPavelist[O, O] = ConcPavelEmpty( this )
}

class PavelNonEmpty[O, T]( val list: List[O], val queue: ApplyQueue[O, _, T] ) extends Pavelist[O, T]
{
    override def add( o: O ): Pavelist[O, T] = new PavelNonEmpty[O, T]( o :: list, queue )
    override def map[U]( pred: Predicate[T, U] ): Pavelist[O, U] = new PavelNonEmpty( list, queue.mapAdd(pred) )
    override def filter( pred: Predicate[T, Boolean] ): Pavelist[O, T] = new PavelNonEmpty( list, queue.filterAdd(pred) )
    override def reduce[U]( pred: (U, T) => U, u: U ): U = queue.reduce( list, pred, u )
    override def collect(): List[T] = reduce( (prev: List[T], cur: T) => prev ::: List(cur), Nil )
    override def conc(): ConcPavelist[O, T] = ConcPavelNonEmpty( this )
}
