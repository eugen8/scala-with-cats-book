package ch1_typeclasses

sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
final case object JsNull extends Json

//The serialize to JSON behaviour
trait JsonWriter[A] {
  def write(value: A): Json
}

/*
* Scala language constructs correspond to the components of type classes as follows:
• traits: type classes;
• implicit values: type class instances;
• implicit parameters: type class use; and
• implicit classes: optional utilities that make type classes easier to use.
*
* Other great (better) resources to understand type classes: https://danielwestheide.com/blog/the-neophytes-guide-to-scala-part-12-type-classes/
* http://baddotrobot.com/blog/2016/08/13/type-classes/
*
* */

/* Cats uses type class patterns: Interface Objects and Interface Syntax */

//Interface Object (place methods in singleton object
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}

final case class Person(name: String, email: String)

//interface objects
object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    (value: String) => JsString(value)
  //equivalent to new JsoWriter[String] { def write (value: String): Json = JsString(value) } but using "single abstract method"

  implicit val personWriter: JsonWriter[Person] =
    (value: Person) => JsObject(Map(
      "name" -> JsString(value.name),
      "email" -> JsString(value.email),
    ))
}

//interface sytax
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = {
      w.write(value)
    }
  }
}
object JsonSyntax2 {
  implicit class JsonWriterOps2[A](value:A){
    def toJson(implicit w: JsonWriter[A]): Json = {
      w.write(value)
    }
  }
}