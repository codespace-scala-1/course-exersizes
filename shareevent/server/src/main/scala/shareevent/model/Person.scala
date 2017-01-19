package shareevent.model


object Role extends Enumeration
{
  type Role = Value
  val Organizer = Value("Organizer")
  val Participant = Value("Participant")
}

case class Person (
                    login: String,
                    password: String,
                    role: Role.Value,
                    email: Option[String] = None,
                    slackId: Option[String] = None,
                    phoneNo: Option[String] = None
                  )

