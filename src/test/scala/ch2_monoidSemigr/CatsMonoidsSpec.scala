package ch2_monoidSemigr

import cats.Monoid
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CatsMonoidsSpec extends AnyFlatSpec with Matchers {

  import cats.Monoid
//  import cats.instances.string._ // for Monoid, not necessary with implicits._
  import cats.implicits._

  "string monoid" should "work" in {
    Monoid[String].combine("hello", "worlddd")
    //    val res0: String = helloworlddd
    Monoid[String].empty
    // res3: String = ""
  }

  "semigrops" should "have empty" in {
    import cats.Semigroup
    Semigroup[String].combine("Hi ", "there")
    // res4: String = "Hi there"
  }

  "The |+| monoid (semigroup) combine operator" should "work on Options" in {
    val combinedOption: Option[Int] = Option(1) |+| Option(2) //SemigroupOps
    println(combinedOption)
    combinedOption shouldBe Option(3)

  }

  it should "work on Maps" in {
    val mapMonoidAdd = Map("a" -> 1, "b" -> 2) |+| Map("b" -> 3, "d" -> 4)
    println(mapMonoidAdd)
    mapMonoidAdd shouldBe Map("a" -> 1, "b" -> 5, "d" -> 4)
  }

  it should "work on Tuples" in {
    val tuple1 = ("hello", 123)
    val tuple2 = ("world", 321)
    tuple1 |+| tuple2
    // res3: (String, Int) = ("helloworld", 444)
  }

  it should "work on ints" in {
    val x = 1 |+| 2 |+| 4
    val y = 1 |+| 2 |+| 4 + Monoid[Int].empty
    //x and y are of type int
    println(y)
    x shouldBe y
    x shouldBe 7
  }
}
