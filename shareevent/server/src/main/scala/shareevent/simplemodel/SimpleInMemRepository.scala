package shareevent.simplemodel

import java.util.concurrent.atomic.AtomicLong

import shareevent.model.{Event, Location, Person}
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}
import shareevent.persistence.Repository

import scala.util.{Success, Try}

class SimpleInMemRepository extends Repository {

  trait IdGenerator[K]
  {
    def gen(): K
  }

  implicit  object LongIdGenerator extends IdGenerator[Long]
  {
    val counter = new AtomicLong(1L)
    def gen(): Long = counter.incrementAndGet()
  }

  class SimpleInMemDAO[K,T](getKey: T=>Option[K],
                            setKey: (K,T)=>T)
                           (implicit idGenerator:IdGenerator[K]) extends DAO[K,T] {

    import shareevent.persistence.QueryDSL._
    import Repository.Objects._

    override def store(obj: T): Try[T] = {
      val (key, withKey) = getKey(obj) match {
        case Some(k) => (k,obj)
        case None => setKey(idGenerator.gen(),obj)
      }
      Success(synchronized {
          ??? //storage = storage.updated(getKey(obj), obj)
      })
    }

    override def retrieve(key: K): Try[Option[T]] = ???

    override def delete(key: K): Try[Boolean] = ???

    override def merge(instance: T): Try[T] = ???

    override def query[MT <: ObjectMeta[T, MT]](q: QueryExpression[MT])(implicit mt: ObjectMeta[T, MT]): Try[Seq[T]] = {
      q match {
        case ts: TableSelectionExpression[_] => Success(queryAll())
        case WhereExpression(ts,where) =>
                          for {selection <- query(ts);
                               predicate = createPredicate(where)
                          } yield selection.filter(predicate)
        case LimitExpression(ts,limit) =>
                          for { selection <- query(ts)}
                            yield selection.take(limit)
        case OffsetExpression(ts,offset) =>
                           for{ selection <- query(ts) }
                             yield selection.drop(offset)

      }
    }

    def createPredicate[MT <: ObjectMeta[T,MT]](where:BooleanExpression): T => Boolean =
    {
      where match {
        case x:FieldComparison =>
                     predicateFromFieldComparison(x)
        case IsNil(fieldExpersion) =>
                     fieldExpersion match {
                       case Constant(t) => _ => t == None
                       case fm: ObjectFieldMeta[_,_,_] =>
                           ///val getter =
                         ???
                     }

      }
    }

    def predicateFromFieldComparison[T](fc:FieldComparison):
                       T => Boolean = ???


    def queryAll(): Seq[T] =
    {
      storage.synchronized {
        storage.values
      }.toStream
    }



    var storage: Map[K,T] = Map()

  }

  override val personDAO: DAO[String, Person] = ???
  override val locationDAO: DAO[Long, Location] = ???
  override val eventsDAO: DAO[Long, Event] = ???
}
