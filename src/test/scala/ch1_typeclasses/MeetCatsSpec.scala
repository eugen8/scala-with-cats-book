package ch1_typeclasses

import java.util.Date

import cats.Show
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MeetCatsSpec extends AnyFlatSpec with Matchers {

  "show instances" should "show ints and strings" in {
    import cats.instances.int._
    val showInt: Show[Int] = Show.apply[Int]
    import cats.instances.all._
    val showString: Show[String] = Show.apply[String]

    val intAsString: String =
      showInt.show(123)

    val stringAsString: String =
      showString.show("abdd")

    println(intAsString)
    println(stringAsString)
    println(showString.show("oops"))
    //    123
    //    abdd
    //    oops

  }

  it should "show easier with interface syntax" in {
    import cats.instances.string._
    import cats.syntax.show._
    import cats.instances.int._

    //***** !!!! ******\\
    val showInt = 123.show
    val showString = "abc".show
    //    val d = new Date()
    //    val showDate = d.show // -date is not supported
    println(showInt)
    println(showString)
  }

  "defining custom instances" should "convert a date with custom implicit" in {
    //this is how Date -> Show[Date] implicit is defined in custom way
    implicit val dateShow: Show[Date] =
      (d: Date) => s"${d.getTime}ms since the epoch"// instead of using Singe Abstract Method could have used: new Show[Date] {def show(d:Date) = s"${d.getTime}ms since the epoch"}

    val showDate: Show[Date] = Show.apply[Date]
    val d = new Date()
    val shownDate = showDate.show(d)
    println(shownDate) //1619959449179ms since the epoch

  }

  it should "convert the date with cats how.show" in {
    implicit val dateShow: Show[Date] =
      Show.show(date =>s"${date.getTime}ms since the epoch")

    val showDate: Show[Date] = Show.apply[Date]
    val now = showDate.show(new Date(1619960291966L)) //1619960291966ms since the epoch
    now shouldBe "1619960291966ms since the epoch"
  }

  "equality" should "compare ints values" in {
    import cats.Eq
    import cats.instances.int._

    val eqInt = Eq[Int]
    eqInt.eqv(123, 123) shouldBe true

    eqInt.eqv(123,432) shouldBe false

//    eqInt.eqv(123, "123")// compilation failure
//    123 == "123" shouldBe false //in scala no compilation failure (intelliJ), but trying to build generates comparing values of types Int and String using `==` will always yield false
  }

  it should "compare simpler using syntax" in {
    import cats.instances.int._
    import cats.syntax.eq._
//    123 === 123 //this won't work if unit tests, interfering with other ===
    123 =!= 123
    MeetCats.equality() shouldBe true //123 === 123
  }

  it should "compare options" in {
    import cats.instances.int._
    import cats.instances.option._
    import cats.syntax.eq._

    Option(1) eqv Option.empty[Int] shouldBe false //Some(1) === None but using Option for type compatibility
  }

  it should "compare custom types" in {
    val date = new Date()
    Thread.sleep(1L)
    val date1 = new Date()
    MeetCats.compareDates(date, date1) shouldBe false
    val dt = new Date()
    MeetCats.compareDates(dt, dt) shouldBe true
    println(date1.getTime, date.getTime)  //(1619962052354,1619962052353)
  }
}
