package pavelist

import model.{Predicate, task}

sealed trait ConcPavelist[O, T] extends Pavelist[O, T]
{
    def list: List[O] = null; // using pavel's list - temp solution probably

    def pavel: Pavelist[O, T]
    def threads: Int

    def offset: Int = pavel.list.size / threads
    def slice(i: Int): List[O] = pavel.list.slice(i * offset, (i + 1) * offset)

    override def add(o: O): ConcPavelist[O, T] = pavel.add( o ).conc()
    override def map[U](pred: Predicate[T, U]): ConcPavelist[O, U] = pavel.map( pred ).conc()
    override def filter(pred: Predicate[T, Boolean]): Pavelist[O, T] = pavel.filter( pred ).conc()
    override def reduce[U](pred: (U, T) => U, u: U): U = pavel.reduce( pred, u )
    override def collect(): List[T] = pavel.collect()
    override def conc(): ConcPavelist[O, T] = this

    def paraReduce[U]( pred: (U, T) => U, u: U, join: (U, U) => U): U
}

// private val list: List[O]
case class ConcPavelEmpty[O]( pavel: PavelEmpty[O], threads: Int = 4  ) extends ConcPavelist[O, O] // PavelEmpty[O](list) with
{
    override def paraReduce[U]( pred: (U, O) => U, u: U, join: (U, U) => U): U = {
        (0 until threads)
            .map { i => task( slice(i).foldRight( u )( (o, u) => pred(u, o) ) ) }
            .map( _.join )
            .reduce( join )
    }
}

// private val list: List[O], private val queue: ApplyQueue[O, _, T]
case class ConcPavelNonEmpty[O, T]( pavel: PavelNonEmpty[O, T], threads: Int = 4 ) extends ConcPavelist[O, T]
{
    // simplified reduce method when reducing on the same type
    // def reduce( pred: (T, T) => T, t: T ): T = paraReduce( pred, t, pred )

    override def paraReduce[U]( pred: (U, T) => U, u: U, join: (U, U) => U ): U = {
        (0 until threads)
            .map { i => task( pavel.queue.reduce( slice(i), pred, u ) ) }
            .map( _.join )
            .reduce( join )
    }
}
