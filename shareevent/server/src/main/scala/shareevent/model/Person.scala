package shareevent.model


trait Person {

  def login: String
  def password: String
  def email: Option[String]
  def slackId: Option[String]
  def phoneNo: Option[String]

}

case class Organizer( login: String,
                       password: String,
                       email: Option[String] = None,
                       slackId: Option[String] = None,
                       phoneNo: Option[String] = None) extends  Person

case class Participant( login: String,
                         password: String,
                         email: Option[String] = None,
                         slackId: Option[String] = None,
                         phoneNo: Option[String] = None) extends  Person

