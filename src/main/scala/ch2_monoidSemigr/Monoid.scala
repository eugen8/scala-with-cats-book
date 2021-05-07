package ch2_monoidSemigr

/**
 * Monoid:  a monoid for a type A is
 * an operation combine with type (A, A) => A
 * an element empty of type A
 */
//trait Monoid[A] {
//  def combine(x: A, y: A): A
//  def empty: A
//}
//defined later


/*
* monoids must formally obey several laws.
* For all values x, y, and z, in A, combine must be associative and empty must be an identity element:
*/


/**
 * A semigroup is just the combine part of a monoid, without the empty part.
 * Cats has a NonEmptyList
 * data type that has an implementation of Semigroup but no implementation
 * of Monoid.
 * Also e.g. PositiveIntegers, NonEmptyLists
 */
trait Semigroup[A] {
  def combine(x: A, y: A): A
}

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit monoid: Monoid[A]): Monoid[A] =
    monoid
}

