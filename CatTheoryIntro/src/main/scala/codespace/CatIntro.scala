package codespace

import scala.annotation.tailrec

object  CatIntro {


  //  A => B
  //

  trait Functor[F[_]]
  {
    self =>

    def map[A,B](a:F[A])(f:A=>B):F[B]

    // map(f:A=>B)(a:F[A]):F[B]
    // map(f:A=>B): F[A]=>F[B]



/*
    def compose[G[_]](g:Functor[G]):Functor[({type L[X]=F[G[X]]})#L] =
      new Functor[F[G]] {
        def map[A,B](a:F[G[A]])(f:A=>B):F[G[B]] =
        {


          self.map[G[A],G[B]](a)((x:G[A])=>g.map(x)(f)) // :  (G[A]=>G[B])=>F[G[B]]
        }
      }
*/

  }


  object OptionFunctor extends Functor[Option]
  {
    def map[A,B](a:Option[A])(f:A=>B):Option[B] = a map f
  }


  object ListFunctor extends Functor[List]
  {
    def map[A,B](a:List[A])(f:A=>B):List[B] = a map f
  }

  trait Apply[F[_]] extends Functor[F]
  {

    def ap[A,B](ff:F[A=>B])(a:F[A]):F[B]

  }

  object OptionApply extends Apply[Option]
  {
    def ap[A,B](ff:Option[A=>B])(fa:Option[A]):Option[B] =
      ff.flatMap(f => fa map (a => f(a)))
    /*
      for{f <- ff
          a <- fa
      } yield f(a)
     */

    def map[A,B](a:Option[A])(f:A=>B):Option[B] = a map f


  }



  trait Applicative[F[_]] extends Apply[F]
  {
    def pure[A](a:A):F[A]

    def pureFun[A,B](f:A=>B):F[A=>B]=pure(f)
  }

  abstract class A
  {
    type B
  }


  //val x: A#B = 1
  //var y: A#B = 2



  //List[({ type L[X]=Map[Int,X]  })#L[Int]]

  //Applicative[Map[Int,_]]

  trait Monadic[F[_]] extends Functor[F]
  {

    def map[A,B](f:A=>B):(F[A] => F[B])



    def flatten[A](ffa:F[F[A]]):F[A]

    def flatMap[A,B](f:A=>F[B]): (F[A] => F[B])  = {
      (fa:F[A]) =>
        flatten(map(f)(fa))
    }

  }

  //
  def f():Int = {
    var x = 1
    for (i <- 1 to 10) {
      x = x * 2
    }
    x
  }

  //Some[X] <: Option[X]
  //val x: Option[Int] = new Some(1)

  def f1():Int = {
    @tailrec
    def s(i:Int, x:Int): Int =
      if (i>10) x else s(i+1,x*2)
    s(1,1)
  }

  //Int => Int

     //F:(A,B)=>C
     // curryF(a): b => f(a,b)

     //g:(A=>(B=>C))



  //def g[F]:(f:Monadic[F]) {
  //}




}
