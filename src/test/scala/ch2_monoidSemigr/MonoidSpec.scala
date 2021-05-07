package ch2_monoidSemigr

import org.scalatest.Assertion
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MonoidSpec extends AnyFlatSpec with Matchers {

  "string addition" should "concatenate" in {
    "one".+("hello") shouldBe "oneh" ++ "ello"
  }

  def associativeLaw[A](x: A, y: A, z: A)
                       (implicit m: Monoid[A]): Boolean = {
    m.combine(x, m.combine(y, z)) ==
      m.combine(m.combine(x, y), z)
  }

  def identityLaw[A](x: A)
                    (implicit m: Monoid[A]): Boolean = {
    (m.combine(x, m.empty) == x) &&
      (m.combine(m.empty, x) == x)
  }

  private def checkLaws(m: Monoid[Boolean], empty: Boolean): Assertion = {
    associativeLaw(true, false, false)(m) shouldBe true
    associativeLaw(false, true, false)(m) shouldBe true
    associativeLaw(false, false, true)(m) shouldBe true

    associativeLaw(true, true, false)(m) shouldBe true
    associativeLaw(true, true, true)(m) shouldBe true

    associativeLaw(false, true, true)(m) shouldBe true
    associativeLaw(true, false, true)(m) shouldBe true
    associativeLaw(true, true, false)(m) shouldBe true

    associativeLaw(false, false, false)(m) shouldBe true

    identityLaw(empty)(m) shouldBe true
    identityLaw(!empty)(m) shouldBe true
  }

  //Exercise 2.3

  "Boolean AND Monoids" should "follow the rules" in {
    class BoolSemigroupAnd extends Semigroup[Boolean] {
      override def combine(x: Boolean, y: Boolean): Boolean = x && y
    }

    class BoolMonoidAnd extends BoolSemigroupAnd with Monoid[Boolean] {
      override def empty: Boolean = true
    }

//    val m = Monoid.apply(new BoolMonoidAnd)
    val m = new BoolMonoidAnd
    val m1 = m.combine(true, false)
    val m2 = m.combine(m1, false)
    println(m2)
    m2 shouldBe false

    checkLaws(m, true)
  }

  "Boolean OR Monoids" should "follow the rules" in {
    class BoolSemigroupOr extends Semigroup[Boolean] {
      override def combine(x: Boolean, y: Boolean): Boolean = x || y
    }

    class BoolMonoidOr extends BoolSemigroupOr with Monoid[Boolean] {
      override def empty: Boolean = false
    }
    val m = new BoolMonoidOr
//    val m = Monoid.apply(new BoolMonoidOr)
    checkLaws(m, false)
  }

  "Boolean XOR Monoids" should "follow the rules" in {
    //one of the book solutions (more concise!)
    implicit val booleanEitherMonoid: Monoid[Boolean] =
      new Monoid[Boolean] {
        def combine(a: Boolean, b: Boolean) =
          (a && !b) || (!a && b)

        def empty = false
      }
    checkLaws(booleanEitherMonoid, false)
  }

  "Boolean XNOR (Exclusive NOR) Monoids" should "follow the rules" in {
    class BoolMonoidNOR extends Monoid[Boolean] {
      override def empty: Boolean = true
//      override def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y) //same thing
      override def combine(x: Boolean, y: Boolean): Boolean = x && y || !x && !y
    }

    implicit val booleanNorMonoid: Monoid[Boolean] = new BoolMonoidNOR()

//    val x = Monoid.apply
//    checkLaws(x)
    checkLaws(booleanNorMonoid, true)

    val x = Monoid[Boolean]
  }

  it should "Monoid companion object has an apply method that returns the type class instance for a particular type" in {
    implicit val boolMonoid: Monoid[Boolean] = new Monoid[Boolean] {
      override def empty: Boolean = true//      override def combine(x: Boolean, y: Boolean): Boolean = (!x || y) && (x || !y) //same thing
      override def combine(x: Boolean, y: Boolean): Boolean = x && y || !x && !y
    }
    val x: Monoid[Boolean] = Monoid[Boolean]
    println(x)
    //ch2_monoidSemigr.MonoidSpec$$anon$2@1b1473ab
    x.isInstanceOf[Monoid[Boolean]] shouldBe true

  }

}
