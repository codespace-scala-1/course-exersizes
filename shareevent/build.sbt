import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq("-unchecked","-deprecation", "-feature"
                         /* ,  "-Ymacro-debug-lite"  */
                         /*,   "-Ydebug"  ,  "-Ylog:lambdalift"  */
                     ),
  libraryDependencies += scalaTest % Test
)


lazy val commons = (project in file("commons")).settings(commonSettings: _*)

lazy val server = (project in file("server")).settings(commonSettings: _*)

lazy val clientParticipant = (project in file("client-participant")).settings(commonSettings: _*)

lazy val clientOrganizer = (project in file("client-organizer")).settings(commonSettings: _*)

lazy val root = (project in file(".")).
  aggregate(commons,server,clientParticipant,clientOrganizer)

