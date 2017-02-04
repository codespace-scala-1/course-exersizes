package shareevent.rdbbased

import shareevent.model.{Person, Role}
import slick.jdbc.H2Profile.api._




object DBSchema {


  implicit val roleColumnType = MappedColumnType.base[Role.Value,Int](
    role => if (role == Role.Organizer) 1 else 0,
    i    => if (i!=0) Role.Organizer else Role.Participant
  )

  class Persons(tag: Tag) extends Table[Person](tag, "Person") {
    def login = column[String]("login", O.PrimaryKey) // This is the primary key column
    def password = column[String]("password")
    def role = column[Role.Value]("role")
    def email = column[Option[String]]("email")
    def slackId = column[Option[String]]("slack_id")
    def phoneNo = column[Option[String]]("column")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (login,password,role,email,slackId,phoneNo) <> (Person.tupled, Person.unapply _ )
  }




  val persons = TableQuery[Persons]


}
