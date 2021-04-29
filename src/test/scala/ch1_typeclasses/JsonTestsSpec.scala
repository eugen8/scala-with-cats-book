package ch1_typeclasses

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class JsonTestsSpec extends AnyFlatSpec with should.Matchers {

  behavior of "Interface Objects"

  it should "print a person via Json.toJson(p) and find implicit JsonWriter[Person] " in {
    import ch1_typeclasses.JsonWriterInstances._
    val j = Json.toJson(Person("Eugen", "eugen@example.com"))
//    println(j)
    j shouldBe JsObject(Map (
      "name" -> JsString("Eugen"),
      "email" -> JsString("eugen@example.com")
    ))
  }

  it should "know how to print a string" in {
    import ch1_typeclasses.JsonWriterInstances._
    val s: String = "hello world"
    //****************** !!!!!!! *****************\\
    val j = Json.toJson(s)
    //JsString(hello world)
    val x: JsonWriter[String] = implicitly[JsonWriter[String]]
    //ch1_typeclasses.JsonWriterInstances$$$Lambda$123/521081105@6e0dec4a
    j shouldBe JsString("hello world")
  }
  //so it's sort of polymorphism, specifically it's an ad-hoc polymorphism


  behavior of "Interface Syntax"

  it should "Print a person via p.toJson and find implicit jsonWriter" in {
    import ch1_typeclasses.JsonSyntax._ //to make a class JsonWriterOps[Person] that will implicitly proide operations JsonWriterOps
    import ch1_typeclasses.JsonWriterInstances._ //to include the implicit parameters
    JsonWriterOps(Person("Eugen", "eugen@example.com")).toJson
    Person("Eugen", "eugen@example.com").toJson //works as we don't import the overlapping implicit
    val x: JsonWriter[Person] = implicitly[JsonWriter[Person]]
    import JsonSyntax2._
    JsonWriterOps2(Person("Eugen", "eugen@example.com")).toJson
    //won't work now
//    Person("Eugen", "eugen@example.com").toJson
    val x2: JsonWriter[Person] = implicitly[JsonWriter[Person]](personWriter)

    // implicitly Summons an implicit value of type T. Usually, the argument is not passed explicitly.
    val d = implicitly[JsonWriter[Person]]
    println(d)//ch1_typeclasses.JsonWriterInstances$$$Lambda$125/2035070981@4b5a5ed1
    println(personWriter)//ch1_typeclasses.JsonWriterInstances$$$Lambda$125/2035070981@4b5a5ed1
    d shouldBe personWriter
  }
}
