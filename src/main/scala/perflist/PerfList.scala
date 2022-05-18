package perflist

import model.Predicate

import scala.annotation.tailrec

// Collect = reduce (pred: Identity)
// PerfEmpty[O] extends [O, O]

trait PerfList[O, T] {
    def add( o: O ): PerfList[O, T]
    def map[U]( pred: Predicate[T, U] ): PerfList[O, U];
    def filter( pred: Predicate[T, Boolean] ): PerfList[O, T];
    def reduce[U]( pred: (U, T) => U, u: U): U;
    def collect(): List[T];
}

case class PerfEmpty[O]( list: List[O] ) extends PerfList[O, O]
{
    override def add( o: O ): PerfList[O, O] = PerfEmpty[O]( o :: list )
    override def map[U]( pred: Predicate[O, U] ): PerfList[O, U] = PerfNonEmpty[O, U]( list, new MapQueue[O, O, U]( None, pred ) )
    override def filter( pred: Predicate[O, Boolean] ): PerfList[O, O] = PerfNonEmpty[O, O]( list, new FilterQueue[O, O]( None, pred ))
    override def reduce[U]( pred: ( U, O ) => U, u: U ): U = u
    override def collect(): List[O] = list
}

case class PerfNonEmpty[O, T](list: List[O], queue: ApplyQueue[O, _, T] ) extends PerfList[O, T]
{
    override def add( o: O ): PerfList[O, T] = PerfNonEmpty[O, T]( o :: list, queue )
    override def map[U]( pred: Predicate[T, U] ): PerfList[O, U] = PerfNonEmpty( list, queue.mapAdd(pred) )
    override def filter( pred: Predicate[T, Boolean] ): PerfList[O, T] = PerfNonEmpty( list, queue.filterAdd(pred) )
    override def reduce[U]( pred: (U, T) => U, u: U ): U = queue.reduce( list, pred, u )
    
    // collect is reduce where the predicate is the identity function
    override def collect(): List[T] = reduce( (prev: List[T], cur: T) => prev ::: List(cur), Nil )
}