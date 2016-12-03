package codespace

object CatIntro {


  //  A => B
  //

  trait Functor[F[_]]
  {
    self =>

    def map[A,B](a:F[A])(f:A=>B):F[B]

    /*
    def compose[G[_]](g:Functor[G]):Functor[F[G]] =
      new Functor[F[G]] {
        def map[A,B](a:F[G[A]])(f:A=>B):F[G[B]] =
        {
          //val f:G[A]=>G[B]
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

  trait Monadic[F[_]] extends Functor[F]
  {

    def map[A,B](f:A=>B):F[A] => F[B]

    def flatMap[A,B](f:A=>F[B]): F[A] => F[B]

  }


  //def g[F]:(f:Monadic[F]) {
  //}


}
