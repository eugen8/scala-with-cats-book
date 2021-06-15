package ch3_functors

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Random

class FunctorsSpec extends AnyFlatSpec with Matchers {

  /** *
   * Functors on their own aren’t so
   * useful, but special cases of functors, such as monads and applicative functors,
   * are some of the most commonly used abstractions in Cats
   * */

  /**
   * a functor is anything with a map method.
   */

  /** *
   * Because map leaves the structure of the context unchanged, we can call it
   * repeatedly to sequence multiple computations on the contents of an initial
   * data structure
   */

  /**
   * think of map not as an iteration pattern, but as a way of sequencing
   * computations
   */

  /** *
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
    val res1 = (func1 map func2) (1)
    println(res1) //2.0

  }

  "List" should "be a Functor" in {
    val a = List(3, 2, 6)
      .map(n => n * 3)
      .map(_ - 9)
      .map(n => s"n=${n}")
    println(a) //List(n=0, n=-3, n=9)
  }

  "functions" should "be Functors and Partial Unification should work" in {
    import cats.instances.function._
    import cats.syntax.functor._

    val func =
      ((x: Int) => x.toDouble).
        map(x => x + 1).
        map(x => x * 2).
        map(x => s"${x}!")
    //scala> val func: Int => String = scala.Function1$$Lambda$896/1060921037@1760e594
    val x = func(123) //248.0!
    println(x) //248.0!
    println(func) //scala.Function1$$Lambda$128/1449263511@5f058f00
  }

  "higher order functions" should "work" in {
    def doubler(a: Int) = a * 2

    //    val x = doubler //doesn't compile
    //    println(doubler) //doesn't compile
    val doublerV = (a: Int) => a * 2
    val xv = doublerV
    println(doublerV, xv, "\n", doublerV(32), xv(32))
    //    (ch3_functors.FunctorsSpec$$Lambda$127/2095303566@78b66d36,ch3_functors.FunctorsSpec$$Lambda$127/2095303566@78b66d36,
    //    ,64,64)

    val simpl = { () =>
      if (Random.nextBoolean() == true) 32
      else 42
    } // val simpl: () => Int
    // if it doesn't have `() =>` then it will be simpl: Int.
    println(simpl) //ch3_functors.FunctorsSpec$$Lambda$128/200224114@69b2283a
    println(simpl()) //42
    println(simpl()) //42
    println(simpl()) //32
  }

  "higher kinds" should "define" in {
    /** Kinds are like types for types. They describe the number of "holes" in a type.
     * We distinguish between regular types that have no holes and "type constructors"
     * that have holes we can fill to produce types. [https://www.scalawithcats.com/dist/scala-with-cats.pdf]
     * also https://www.baeldung.com/scala/higher-kinded-types */
    /** For example, List is a type constructor with one hole. We fill that hole by
     * specifying a parameter to produce a regular type like List[Int] or List[A].
     * The trick is not to confuse type constructors with generic types. List is a type
     * constructor, List[A] is a type: */

    /** This is a type constructor, takes parameters */
    val lTypeConstr = List
    /** type, produced by applying a type parameter */
    val lType: Seq[Int] => List[Int] = List[Int] //won't compile without ": Seq[Int] => List[Int]"
    // missing argument list for method apply in trait IterableFactory
    // Unapplied methods are only converted to functions when a function type is expected.
    // You can make this conversion explicit by writing `apply _` or `apply(_)` instead of `apply`.
    println(lTypeConstr, lType, lType(Seq(2, 1, 4)))
    //(scala.collection.immutable.List$@6af93788,
    // ch3_functors.FunctorsSpec$$Lambda$126/796667727@ef9296d,
    // List(2, 1, 4))
    println(lTypeConstr(32, 2, 5, 3)) //List(32, 2, 5, 3)
    println(lTypeConstr.getClass) //class scala.collection.immutable.List$
    val l2: List[Int] = List(3, 2, 4)
    println(l2.getClass) //class scala.collection.immutable.$colon$colon
  }

  it should "be analogous to functions" in {
    val a: Double => Double = math.abs // function, takes one parameter
    val b = math.abs(-321) // value, produced by applying a value parameter
    println(a, b) //(ch3_functors.FunctorsSpec$$Lambda$127/2095303566@78b66d36,321)
  }

//  it should "declare type constructors using underscores" in {
  //    import cats._
  //    import cats.implicits._
  //
  //    def myMethod[F[_]] = {
  //      // Reference F without underscores:
  ////      val functor = Functor.apply[F]
  //      println(s"Declaring ${this}")
  //    }
  //  val m: Unit = myMethod[List[Int]]
  //  ERROR: List[Int] takes no type parameters, expected: 1
//  }


}
