package pavelist

import model.task

sealed trait ConcPavelist[O, T] extends Pavelist[O, T]
{
    def paraReduce[U]( pred: (U, T) => U, u: U, join: (U, U) => U): U
}

case class ConcPavelEmpty[O]( private val list: List[O] )
    extends PavelEmpty[O](list) with ConcPavelist[O, O]
{
    override def paraReduce[U]( pred: (U, O) => U, u: U, join: (U, U) => U): U = reduce(pred, u)
}

case class ConcPavelNonEmpty[O, T]( private val list: List[O], private val queue: ApplyQueue[O, _, T], threads: Int = 4 )
    extends PavelNonEmpty[O, T](list, queue) with ConcPavelist[O, T]
{
    val offset: Int = list.size / threads
    def slice(i: Int): List[O] = list.slice(i * offset, (i + 1) * offset)

    // simplified reduce method when reducing on the same type
    // def reduce( pred: (T, T) => T, t: T ): T = paraReduce( pred, t, pred )

    override def paraReduce[U]( pred: (U, T) => U, u: U, join: (U, U) => U ): U = {
        (0 until threads)
            .map { i => task( queue.reduce( slice(i), pred, u ) ) }
            .map( _.join )
            .reduce( join )
    }
}
