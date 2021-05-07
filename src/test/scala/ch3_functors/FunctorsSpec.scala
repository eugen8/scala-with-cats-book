package ch3_functors

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FunctorsSpec extends AnyFlatSpec with Matchers {

  /***
   * Functors on their own aren’t so
   * useful, but special cases of functors, such as monads and applicative functors,
   * are some of the most commonly used abstractions in Cats
   * */

  /**
   * a functor is anything with a map method.
   */

  /***
   * Because map leaves the structure of the context unchanged, we can call it
   * repeatedly to sequence multiple computations on the contents of an initial
   * data structure
   */

  /**
   * think of map not as an iteration pattern, but as a way of sequencing
   * computations
   */

  /***
   * Examples: Option, List, Either, Future, etc.
   * Future isn't referentially transparent though
   */

  /**
   * It turns out that single argument functions are also functors.
   */


  "single argument functions" should "be functors" in {
    val func1: Int => Double =
    (x: Int) => x.toDouble
    val func2: Double => Double =
    (y: Double) => y * 2
    import cats.instances.function._
    import cats.syntax.functor._ // for map
//    import cats.implicits._
    val res1 = (func1 map func2)(1)
    println(res1) //2.0

  }

  "List" should "be a Functor" in {
    val a = List(3,2,6)
      .map(n =>n * 3)
      .map(_ - 9)
      .map(n => s"n=${n}")
    println(a)//List(n=0, n=-3, n=9)
  }

  "partial unification" should "work" in {
    import cats.instances.function._ // for Functor
    import cats.syntax.functor._

    val func =
      ((x: Int) => x.toDouble).
        map(x => x + 1).
        map(x => x * 2).
        map(x => s"${x}!")
    val x= func(123) //248.0!
    println(x)

  }
  it should "simply allow to map on numbers" in {
    import cats.implicits._
    import cats._

    val double = ((x:Int ) => x).map(x =>x * x)(14)
    println(double)

  }


  "higher kinds" should "define" in {
    val x: List.type = List
    val d: Seq[Int] => List[Int] = x[Int]
    val seq = d(Seq(3,2,4)) //List(3, 2, 4)

    println(seq)

  }
}
