package various

import cats.Functor
import cats.data.OptionT
import cats.implicits.catsStdInstancesForList

class VariousOne {

  val ls: Functor[List] = implicitly[Functor[List]]
  val a: OptionT[List, String] = OptionT.liftF[List, String](List("32","902w"))

}
