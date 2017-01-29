package shareevent.simplemodel

import java.util.concurrent.atomic.AtomicLong

import cats.arrow.FunctionK
import cats.{Apply, Id, Monad, ~>}
import shapeless.LabelledGeneric
import shareevent.model.{Event, Location, PersistenceId, Person}
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}
import shareevent.persistence.Repository

import scala.util.{Success, Try}
import scala.reflect.runtime._


class SimpleInMemRepository(implicit override val mMonad:Monad[Try],
                                     override val tryToMonad: ~>[Try,Try]) extends Repository[Try] {


  trait IdGenerator[K] {
    def gen(): K
  }

  implicit object LongIdGenerator extends IdGenerator[Long] {
    val counter = new AtomicLong(1L)

    def gen(): Long = counter.incrementAndGet()
  }

  implicit object StingIdGenerator extends IdGenerator[String] {

    def gen(): String = LongIdGenerator.gen().toString
  }

  implicit def PeristenceIdIdGenerator[K,T](implicit keyGenerator: IdGenerator[K]): IdGenerator[PersistenceId[K,T]] = new IdGenerator[PersistenceId[K,T]] {
    override def gen(): PersistenceId[K, T] = new PersistenceId[K,T](keyGenerator.gen())
  }

  class SimpleInMemDAO[K, T](getKey: T => Option[K],
                             setKey: (K, T) => T)
                            (implicit idGenerator: IdGenerator[K],
                                      mt: ObjectMeta[T,_]) extends DAO[K, T] {

    import shareevent.persistence.QueryDSL._
    import Repository.Objects._

    override def store(obj: T): Try[T] = {
      val (key, withKey) = getKey(obj) match {
        case Some(k) => (k, obj)
        case None => val newKey = idGenerator.gen()
          (newKey, setKey(newKey, obj))
      }
      this.synchronized {
        storage = storage.updated(key, withKey)
      }
      Success(withKey)
    }

    override def retrieve(key: K): Try[Option[T]] = {
      Success(storage.get(key))
    }

    override def delete(key: K): Try[Boolean] = {
      Try(synchronized {
        if (storage.isDefinedAt(key)) {
          storage -= key
          true
        } else false
      })
    }

    override def merge(instance: T): Try[T] =
      Try {
        synchronized {
          getKey(instance) match {
            case Some(k) => storage = storage.updated(k, instance)
              instance
            case None => throw new IllegalStateException("attempt to merge undetached instance")
          }
        }
      }

    override def query[MT <: ObjectMeta[T, MT]](q: QueryExpression[MT]): Try[Seq[T]] = {
      q match {
        case ts: TableSelectionExpression[_] => Success(queryAll())
        case WhereExpression(ts, where) =>
          for {selection <- query(ts);
               predicate = createPredicate(where)
          } yield selection.filter(predicate)
        case LimitExpression(ts, limit) =>
          for {selection <- query(ts)}
            yield selection.take(limit)
        case OffsetExpression(ts, offset) =>
          for {selection <- query(ts)}
            yield selection.drop(offset)

      }
    }

     def createPredicate[MT <: ObjectMeta[T, MT]](where: BooleanExpression) : T => Boolean = {
        x =>  createMapPredicate[MT](where)(mt.toMap(x))
     }


      def createMapPredicate[MT <: ObjectMeta[T, MT]](where: BooleanExpression): Map[Symbol,Any] => Boolean = {
      where match {
        case x: FieldComparison =>
          predicateFromFieldComparison(x.asInstanceOf[FieldComparison.Aux[x.FieldType]])
        case IsNil(fieldExpersion) =>
          fieldExpersion match {
            case Constant(t) => _ => t == None
            case fm: ObjectFieldMeta[_, _, _] =>
              v => valueFromFieldExpression(fieldExpersion)(v) == None
          }
        case AndBooleanExpression(x, y) =>
          t => createMapPredicate(x)(t) && createMapPredicate(y)(t)
        case OrBooleanExpression(x, y) =>
          t => createMapPredicate(x)(t) || createMapPredicate(y)(t)
        case NotBooleanExpression(x) =>
          t => !createMapPredicate(x)(t)
      }
    }


    def predicateFromFieldComparison[F](fc: FieldComparison.Aux[F]): Map[Symbol,Any] => Boolean = {
      val getX = valueFromFieldExpression[F](fc.x)
      val getY = valueFromFieldExpression[F](fc.y)
      fc match {
        case Equals(fx,fy)    => v => getX(v) == getY(v)
        case NonEquals(fx,fy) => v => (getX(v) != getY(v))
        case Less(fx,fy,o)    => v => o.lt(getX(v),getY(v))
        case LessEq(fx,fy,o)  => v => o.lteq(getX(v),getY(v))
        case Greater(fx,fy,o) => v => o.gt(getX(v),getY(v))
        case GreaterEq(fx,fy,o)=> v => o.gteq(getX(v),getY(v))
      }
    }

    def valueFromFieldExpression[F](fe:FieldExpression[F]): Map[Symbol,Any] => F =
    {
      fe match {
        case Constant(x) =>  _ => x
        case fm: ObjectFieldMeta[_,_,_] => _.get(Symbol(fm.name)).get.asInstanceOf[F]
      }
    }


    def queryAll(): Seq[T] = {
      storage.synchronized {
        storage.values
      }.toStream
    }


    var storage: Map[K, T] = Map()

  }

  import Repository.Objects._

  override val personDAO: DAO[String, Person] = new SimpleInMemDAO[String,Person](
                                                      p => Some(p.login), (k,p)=>p.copy(login=k))
  override val locationDAO: DAO[Location.Id, Location] = new SimpleInMemDAO[Location.Id,Location](
                                                       _.id, (k,t) => t.copy(id=Some(k))
                                                 )
  override val eventsDAO: DAO[Event.Id, Event] = new SimpleInMemDAO[Event.Id,Event](
                                                        _.id, (k,t) => t.copy(id=Some(k))
                                                 )

}
