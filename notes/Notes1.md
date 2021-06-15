# Notes from reading the book
Plus from various research

---

**Type classes**: In scala they are interfaces (providing an APIs) that represent some functionality
we want to implement. They originated in Haskel. allow us to unify disparate data types with a common interface.

**Type class instance**: provides an implementation for the type class. 
It is defined by created an implementation of the type class (trait) 
and tagging them with `implicit` keyword [link].

(ss)[https://www.scalawithcats.com/dist/scala-with-cats.html#anatomy-of-a-type-class]

Type class usage is achieved through either: 
 * Interface Objects
 * Interface syntax - which uses _extension methods_

[scalabooks-anatomy of typeclass](https://www.scalawithcats.com/dist/scala-with-cats.html#anatomy-of-a-type-class)


### IntelliJ Notes:
https://www.jetbrains.com/help/idea/edit-scala-code.html#implicit_methods_arguments
 * `CTRL+Q` to invoke the list of applicable implicit conversions
 * `Cmd+Shift+P` Show implicit arguments (It might be helpful for code analyzing when you want to find out what implicit arguments were passed to the particular call. )
 * `Ctrl+Shit+P` Show type Info for a variable (same with mouse-over)
 * `Cmd+P` Show parameters
note - These two differ between the docs and scala plugin mappings