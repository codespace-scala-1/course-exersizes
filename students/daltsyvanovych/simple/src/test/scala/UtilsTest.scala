
import org.scalatest.FunSuite
import Utils._


class UtilsTest extends FunSuite{

  test("string must be converted to digit") {
    assert(Some(123) === parseInt("123"))
  }

  test("string can't be converted to digit") {
    assert(None === parseInt("sdf"))
  }

  test("parse ip address") {
    assert(Some(168100101) === parseIp("10.5.1.5"))
  }

  test("can't parse ip address") {
    assert(None === parseIp("asdf.adsf.a.sdf"))
  }

}
