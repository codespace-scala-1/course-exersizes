import org.scalatest._
import yuri.phonecode._

class PhonecodeTest extends FunSuite {

  test("existing vocabulary file") {
    val lookup = new ReverseLookup("data/test.w")
    //assert(lookup.lookup )
  }

  test("vocabulary file with wrong name") {
    val lookup = new ReverseLookup("data/test1.w")
    //assert(lookup.lookup === None)
  }

  test("lookup collects aliases") {
    val lookup = new ReverseLookup("data/test.w")
    assert(lookup.lookup.get("4824") === Seq("fort", "Torf"))
    assert(lookup.lookup.get("562") === Seq("mir", "Mix"))
  }

  test("actual encoding") {
    val lookup = new ReverseLookup("data/test.w")
    val encoder = new PhonecodeEncoder(lookup)

    assert(encoder.getValidEncodings("0721/608-4067") === Seq(), "no match")
    assert(encoder.getValidEncodings("5624-82").sorted === Seq("Mix Tor", "mir Tor").sorted, "no digits")
    assert(encoder.getValidEncodings("4824").sorted === Seq("Torf", "fort", "Tor 4").sorted, "with last digit")
    assert(encoder.getValidEncodings("10/783--5").sorted === Seq("neu o\"d 5", "je bo\"s 5", "je Bo\" da").sorted, "10/783--5")
    assert(encoder.getValidEncodings("381482") === Seq("so 1 Tor"), "digit in the middle")
    assert(encoder.getValidEncodings("04824").sorted === Seq("0 Torf", "0 fort", "0 Tor 4").sorted, "leading skip only")
  }
}