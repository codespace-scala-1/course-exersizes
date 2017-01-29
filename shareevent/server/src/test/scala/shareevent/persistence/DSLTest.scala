package shareevent.persistence

import org.scalatest.{FunSpec, FunSuite}
import shareevent.model.Person


class DSLTest extends FunSuite {

  test("queryDSL can be created ") {
     import QueryDSL._
     import Repository.Objects._

     val qMe = personMeta.select where ((personMeta.login === "me") && personMeta.phoneNo.isNil)

     System.err.println(s"DSLTest: qMe=${qMe}")

     assert(qMe.isInstanceOf[QueryExpression[_]])


  }


}
