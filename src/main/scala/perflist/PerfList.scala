package perflist

import scala.annotation.tailrec


trait PerfList[O, T] {
    type Predicate[U, V] = (U, Int) => V
    def add( o: O ): PerfList[O, T]
    def map[U]( pred: Predicate[T, U] ): PerfList[O, U];
    def filter( pred: Predicate[T, T] ): PerfList[O, T];
    def reduce[U]( pred: (U, T) => U, u: U): U;
}

case class PerfEmpty[O, T](list: List[O] ) extends PerfList[O, T]
{
    override def add( o: O ): PerfList[O, T] = PerfEmpty[O, T]( o :: list )
    override def map[U]( pred: Predicate[T, U] ): PerfList[O, U] = PerfNonEmpty[O, U]( list, new ApplyQueue[O, T, U]( None, pred ) )
    override def filter( pred: Predicate[T, T] ): PerfList[O, T] = PerfNonEmpty[O, T]( list, new ApplyQueue[O, T, T]( None, pred ))
    override def reduce[U]( pred: ( U, T ) => U, u: U ): U = u
}

case class PerfNonEmpty[O, T](list: List[O], queue: ApplyQueue[O, _, T] ) extends PerfList[O, T]
{
    override def add( o: O ): PerfList[O, T] = PerfNonEmpty[O, T]( o :: list, queue )
    override def map[U]( pred: Predicate[T, U] ): PerfList[O, U] = PerfNonEmpty( list, queue.add(pred) )
    override def filter( pred: Predicate[T, T] ): PerfList[O, T] = PerfNonEmpty( list, queue.add(pred) )
    
    override def reduce[U]( pred: (U, T) => U, u: U ): U = {
        @tailrec
        def apply(l: List[O], i: Int, acc: U ): U = l match {
            case x :: xs => apply( xs, i + 1, pred( acc, queue.apply(x, i) ) )
            case _ => acc;
        }
        apply(list, 0, u);
    }
}