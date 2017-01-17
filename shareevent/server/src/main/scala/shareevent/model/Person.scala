package shareevent.model


object Role extends Enumeration
{
  val Organizer,Participant = Value
}

case class Person (
                    login: String,
                    password: String,
                    role: Role.Value,
                    email: Option[String] = None,
                    slackId: Option[String] = None,
                    phoneNo: Option[String] = None
                  )

