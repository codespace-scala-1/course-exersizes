package shareevent.persistence.inmem

import cats._
import cats.instances.all._
import shareevent._
import org.joda.time.DateTime
import shareevent.DomainContext
import shareevent.model.{Event, Location, Person}
import shareevent.persistence.QueryDSL.{ObjectMeta, QueryExpression}
import shareevent.persistence.Repository
import shareevent.simplemodel.SimpleInMemRepository

import scala.util.{Success, Try}
import scala.language.higherKinds._


class InMemoryContext extends DomainContext[Try] {


  def checkExistence[T](op:Option[T]): Try[T] =
    op.toRight(new IllegalStateException("login not found")).toTry

  override val repository = new SimpleInMemRepository() 

  override def currentTime: DateTime = new DateTime()

}
