package various

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ApplicativePlay extends AnyFlatSpec with Matchers {


  case class Blah(id: Option[Int] = None, txId: String, otherCooks: Long)

  import cats.Functor

  trait Applicative[F[_]] extends Functor[F] {
    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]

    def pure[A](a: A): F[A]

    def map[A, B](fa: F[A])(f: A => B): F[B] = ap(pure(f))(fa)
    def x[A,B](f: A=>B): F[A => B] = pure(f)
  }

  sealed trait Cont[+A]{ //can't return Empty when exepcting Cont[X] if it's not +A but just A
    def get: A
  }
  case object Empty extends Cont[Nothing] {
    override def get: Nothing = ???
  }
  case class Full[A](a: A) extends Cont[A] {
    def get: A = a
  }

  class IntApp extends Applicative[Cont]{
    override def ap[A, B](ff: Cont[A => B])(fa: Cont[A]): Cont[B] = (fa, ff) match {
      case (Full(a), Full(f)) => Full( f(a) )
      case _ => Empty
    }
    override def pure[A](a: A): Cont[A] = Full(a)
  }

  val ia1 = new IntApp().pure(29)
  println(ia1)//Full(29)
  
  val o1 = Option(12)
  println(s"o1 = ${o1}")//o1 = Some(12)
  val ia2 = new IntApp().pure(19)
  val ia3 = new IntApp().ap(Full( (x: Int) => x * 2 ))(ia1)
  println(s"ia2 = ${ia2}")
  println(s"ia3 = ${ia3}")

}
