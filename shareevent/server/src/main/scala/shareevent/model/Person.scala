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


object Person
{
  type Id = String
}


trait OrganizerTag

object OrganizerTag {

   def tag[T](x:T): T @@ OrganizerTag =
     toTagged[T,OrganizerTag](x)

}


trait ParticipantTag

object ParticipantTag
{


  def tag[T](x:T): T @@ ParticipantTag =
    toTagged[T,ParticipantTag](x)

}