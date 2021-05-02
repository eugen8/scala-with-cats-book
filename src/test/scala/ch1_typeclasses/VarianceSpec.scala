package ch1_typeclasses

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class VarianceSpec extends AnyFlatSpec with should.Matchers {

  sealed trait Shape

  case class Circle(radius: Double) extends Shape

  //covariant
  class Box[+A](val value: A) {
    def map[B](f: A => B): Box[B] = new Box(f(value))

    //    def unboxWith(a: A): Unit = ??? //Can't do it, Covariant type A occurs in contravariant position in type A of value a
  }

  //contravariant
  trait JsonWriter[-A] {
    def write(value: A): Json
  }

  val shapeWriter: JsonWriter[Shape] = new JsonWriter[Shape] {
    override def write(value: Shape): Json = JsObject(Map("shape" -> JsString("undefined")))
  }
  val circleWriter: JsonWriter[Circle] = (c: Circle) => JsObject(Map("circle r=" -> JsNumber(c.radius)))

  def format[A](value: A, writer: JsonWriter[A]): Json =
    writer.write(value)

  "variance" should "allow types correctly" in {

    val v0 = format(new Shape() {}, shapeWriter)

    //  format(new Shape(){}, circleWriter)
    // type mismatch;
    //  found   : VarianceSpec.this.JsonWriter[VarianceSpec.this.Circle]
    //  required: VarianceSpec.this.JsonWriter[VarianceSpec.this.Shape]

    val v1 = format(new Circle(20.0), shapeWriter)
    val v2 = format(new Circle(2.0), circleWriter)

    println(s"v0 = $v0 \n v1 = $v1 \n v2=$v2")
    //    v0 = JsObject(Map(shape -> JsString(undefined)))
    //     v1 = JsObject(Map(shape -> JsString(undefined)))
    //     v2=JsObject(Map(circle r= -> JsNumber(2.0)))
  }

  behavior of "Invariance"

  class Container[A](value: A) {
    private var _value: A = value
    def getValue: A = _value
    def setValue(value: A): Unit = {
      _value = value
    }
  }

  abstract class Animal {
    def name: String
  }

  case class Cat(name: String) extends Animal

  case class Dog(name: String) extends Animal

  //https://docs.scala-lang.org/tour/variances.html

  it should "not allow sub or super types on invariant types" in {
    val catContainer: Container[Cat] = new Container(Cat("Felix"))
    //val animalContainer: Container[Animal] = catContainer
    /* type mismatch; found   : VarianceSpec.this.Container[VarianceSpec.this.Cat]; required: VarianceSpec.this.Container[VarianceSpec.this.Animal]
    Note: VarianceSpec.this.Cat <: VarianceSpec.this.Animal, but class Container is invariant in type A. You may wish to define A as +A instead. (SLS 4.5)*/

    //animalContainer.setValue(Dog("Spot"))
    val cat: Cat = catContainer.getValue // Oops, we'd end up with a Dog assigned to a Cat
    val animalContainer2: Container[Animal] = new Container(new Animal{
      override def name: String = "Animal1"})
    animalContainer2.setValue(Dog("Rex")) // can do
//    val catContainer2: Container[Cat] = animalContainer2 //Note: VarianceSpec.this.Animal >: VarianceSpec.this.Cat, but class Container is invariant in type A.
    // You may wish to define A as -A instead. (SLS 4.5)
  }

//  functions are contravariant in their parameter types and covariant in their result types.
//  https://docs.scala-lang.org/tour/lower-type-bounds.html

}
