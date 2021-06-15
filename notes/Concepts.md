# Concepts

## Context Bound 
This [stackOVerflow](https://stackoverflow.com/questions/4465948/what-are-scala-context-and-view-bounds/4467012#4467012)
explanation is still the best I could find. 

That is linked from a very simple explanation here 
[scala2-lang-faq](https://docs.scala-lang.org/tutorials/FAQ/index.html)
`It’s syntactic sugar for an implicit parameter of type Foo[T].` - that's basically what it is
And why is in needed - in short again is for creating `type classes` which simply is an alternative to tranditional object oriented
inheritance and polymorphism. It is a syntactic sugar so you can pass a _context parameter_ down to your method, so to pass some implicit
value from outside your method - into your method.
It is not the same as "view bound" that has been deprecated and it is(was) an implicit conversion. Context bound is 
an implicit value. Details on stackOverflow, [scala2-lang-faq](https://docs.scala-lang.org/tutorials/FAQ/index.html) or
[scala3-context-bounds](https://docs.scala-lang.org/scala3/book/ca-context-bounds.html)

## Functional Effects
Functional effects are immutable data structures that merele _describes_ sequences of operations
https://www.youtube.com/watch?v=p98W4bUtbO8
https://degoes.net/articles/zio-environment#:~:text=A%20functional%20effect

At the end of the world, the data structure has to be impurely "interpreted" to real world effects.

IO[A] - A description of an effect that when unsafely interpreted, will succeed with a value of type A

## Monoid
A monoid for a type `A` is 
 1. a `combine` operation with type `(A, A) => A`
 2. an `empty` identity element of type `A`

It must also satisfy 
 1. associativity for the combine operation:
`combine(x, combine(y,z)) == combine(combine(x,y),z)`.
 2. empty has to be the identity element: `(m.combine(x, m.empty) == x) &&
(m.combine(m.empty, x) == x)`
    
## Semigroup: 
A semigroup is just the combine part of a monoid, without the empty part

Cats has: `Semigroup[B]` and `Monoid[B]`, there is also the Monoid object, so we can do e.g.
`Monoid[String].combine("Hi ", "there")`

Monoid/semigroup syntax in cats: `val stringResult = "Hi " |+| "there" |+| Monoid[String].empty`

It is useful when we want to combine many data elements together, e.g. in big or distributed systems to
combine partial results to get the final result.


## Functor
 In scala or Cats or other progr. languages... functor allows some mapping!
 E.g. in scala it can be thought of a "type class" that abstract over type constructors F[_] to allow mapping over them.
so
 ```scala
 trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
 }
```
with an example implementation:
```scala
implicit val functorForOption: Functor[Option] = new Functor[Option] {
  def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
    case None    => None
    case Some(a) => Some(f(a))
  }
}
```
The F in Functor is often referred to as an "effect" or "computational context.".


Functors compose

https://typelevel.org/cats/typeclasses/functor.html

### Higher kinds and Type constructors
Higher kinds - are like types for types. They describe the number of "holes" ink a type.

---

### Applicative 

Applicative extends Functor with an ap and pure method.

pure wraps the value into the type constructor


