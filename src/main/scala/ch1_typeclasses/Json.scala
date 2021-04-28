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
* */

/* Cats uses type class patterns: Interface Objects and Interface Syntax */

//Interface Object (place methods in singleton object
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]): Json =
    w.write(value)
}