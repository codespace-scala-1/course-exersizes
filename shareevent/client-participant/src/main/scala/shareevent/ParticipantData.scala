package shareevent

case class ParticipantData (
                    login: String,
                    password: String,
                    email: Option[String] = None,
                    slackId: Option[String] = None,
                    phoneNo: Option[String] = None
                  )