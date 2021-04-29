package ch1_typeclasses

import java.util.Date

import cats.Eq

object MeetCats {
  def compareDates(date: Date, date1: Date) = {
    import cats.instances.long._
    import cats.syntax.eq._
    implicit val dateEq: Eq[Date] = Eq.instance[Date] { (date1, date2) =>
      date1.getTime === date2.getTime
    }

     date === date1
  }

  def equality(): Boolean ={
    import cats.instances.int._
    import cats.syntax.eq._
    123 === 123
  }
}
