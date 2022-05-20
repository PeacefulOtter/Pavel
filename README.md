
Pavel is a project aimed at implementing highly performant operations like `map`, `filter`, `reduce` on Scala Lists.
A concurrency version is implemented to perform all the below operations using multiple threads.

Supports:

```scala
.map[U]( pred: (elt: T, i: Int) => U )
.filter( pred: (elt: T, i: Int) => Boolean )
.reduce[U]( pred: (acc: U, cur: T) => U, u: U )
.collect()
.print()
```

The entire project is using [Functional Programming](https://fr.wikipedia.org/wiki/Functional_Programming)

### Usage

##### Example with map - filter - map - collect

```scala
import pavelist.PavelEmpty

val myList: List[Int] = List(1, 2, 3, 4, 5)
val pavelList = PavelEmpty(myList)
    .map( (elt: Int, i: Int) => elt * 3 )
    .filter( (elt: Int, i: Int) => (elt % 2) == 0 )
    .map( (elt: Int, i: Int) => elt / 2 )
    .collect()
```

##### Example with reduce (=foldLeft) to sum the elements of a list
```scala
import pavelist.PavelEmpty

val pavelList = PavelEmpty(myList)
    .reduce( (acc: Int, cur: Int) => acc + cur, 0 )
```
