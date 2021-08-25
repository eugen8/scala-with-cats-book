import scala.collection.mutable.{Map => MutMap}

object LetterFinder extends App {
  //This has O(n) complexity. Still not completely satisfied with mutable map, will think of a better way to
  //implement it in a more functional style
  def findBetterUnique(text: String): Option[String] = {
    val charsMap = text.foldLeft(MutMap[Char, Int]())((ag, c) => {
      ag.get(c) match {
        case Some(value) => ag.update(c, value + 1)
        case None => ag.put(c, 1)
      }
      ag
    })
    text.find(l => charsMap.get(l).contains(1)).map(_.toString) //or could have left it as char
  }
  
  println(findBetterUnique("abcabd") == Some("c"))
  println(findBetterUnique("a") == Some("a"))
  println(findBetterUnique("acbcabd") == Some("d"))
  println(findBetterUnique("acbcdabcd") == None)
  println(findBetterUnique("") == None)
  
  
  //Initial, more naive implementation, complexity O(n^2)
  def findUnique(text: String): String =
    text.foldLeft((false, ""))((agg, el) => {
      agg match {
        case (false, _) =>
          (text.indexOf(el) == text.lastIndexOf(el), el.toString)
        case _ => agg
      }
    })._2


  println(findUnique("abcabd") == "c")
  println(findUnique("a") == "a")
  println(findUnique("acbcabd") == "d")
  println(findUnique("") == "")
  
  // a text.find(...) implementation would be a much simpler solution than findUnique here, but still with O(n^2) complexity.
}
