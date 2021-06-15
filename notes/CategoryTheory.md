# Category Theory

* Wiki: https://en.wikipedia.org/wiki/Category_(mathematics) is a start (and also a rabithole).
* https://iep.utm.edu/set-theo/#SH3b
  
Set theories:
* von Neumann–Bernays–Gödel set theory (NBG)
* Zermelo–Fraenkel set theory (ZFC)

A category (aka abstract category) is a collection of "objects" that are linked by "arrows".

A *category* C consists of:
 * a class ob(C) of objects
 * a class hom(C) of arrows, or _maps_, or morphisms between the objects 
 * a domain, or source object class function dom:hom(C) -> ob(C)
 * a codomain (target object class function) cod:hom(C) -> ob(C)
 * a composition of morphisms: `hom(a,b) x hom(b,c) -> hom(a,c)`, or `f : a → b` 
   and `g : b → c` is written as `g ∘ f` or `gf`.
 * associativity: if `f : a → b`, `g : b → c` and `h : c → d` then `h ∘ (g ∘ f) = (h ∘ g) ∘ f`
 * identity: for every object x, there exists a morphism 1x : x → x (some authors write idx) called the identity morphism for x, such that every morphism f : a → x satisfies 1x ∘ f = f, and every morphism g : x → b satisfies g ∘ 1x = g.   

![](assets/img-obj-arrows-wikipedia.png)

There are some cool properties - one of which is `isomorphism` - meaning you can get a reverse mapping

---

A function f from a set X to a set Y is surjective (also known as onto, or a surjection), if for every element y in the codomain Y of f, there is at least one element x in the domain X of f such that f(x) = y.[1][2][3] It is not required that x be unique; the function f may map one or more elements of X to the same element of Y.

An injective function (also known as injection, or one-to-one function) is a function that maps distinct elements of its domain to distinct elements of its codomain.

A bijection, bijective function, one-to-one correspondence, or invertible function, is a function between the elements of two sets, where each element of one set is paired with exactly one element of the other set, and each element of the other set is paired with exactly one element of the first set. There are no unpaired elements.
So a bijective function f: X → Y is a one-to-one (injective) and onto (surjective).


A book: Abstract and concrete categories: http://katmat.math.uni-bremen.de/acc/acc.htm (pdf: http://katmat.math.uni-bremen.de/acc/acc.pdf ) 

---
In mathematics, a set is a collection of distinct elements.

Rings — algebraic structures in which addition and multiplication are defined and have similar properties to those operations defined for the integers.

Field - is a set on which addition, subtraction, multiplication, and division are defined and behave as the corresponding operations on rational and real numbers do. 

Functor is a mapping between categories. Let C, D be two categories, a Functor
 1. Associates each object X in C to an object F[X] in D (equivalent to `lift` in scala)
 2. Associates each morphism f:X->Y in C to a morphism F(f):F[X]->F[Y] such that it preserves 
    * Identity morphisms 
    * Composition of morphisms 
