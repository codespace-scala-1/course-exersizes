val akkaVersion = "2.4.16"
val akkaHttpVersion = "10.0.1"

lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq("-unchecked","-deprecation", "-feature"
                         /* ,  "-Ymacro-debug-lite"  */
                         /*,   "-Ydebug"  ,  "-Ylog:lambdalift"  */
                     ),
  libraryDependencies ++= Seq (
    scalaVersion( "org.scala-lang" % "scala-reflect" % _ ).value,
    "org.scalatest" %% "scalatest" % "3.0.1" % Test,
    "com.github.nscala-time" %% "nscala-time" % "2.16.0",
    "com.chuusai" %% "shapeless" % "2.3.2",
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "org.json4s" %% "json4s-native" % "3.5.0",
    "org.json4s" %% "json4s-ext" % "3.5.0",
    "de.heikoseeberger" %% "akka-http-json4s" % "1.11.0",
    "org.typelevel" %% "cats" % "0.9.0",
    "com.typesafe.akka" %% "akka-persistence" % "2.4.16"
  )
)



lazy val commons = (project in file("commons")).settings(commonSettings: _*)

lazy val server = (project in file("server"))
  .settings(commonSettings: _*)
  .settings(
    resolvers += Resolver.bintrayRepo("hseeberger", "maven"),
    libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
  )

lazy val clientParticipant = (project in file("client-participant")).settings(commonSettings: _*)

lazy val clientOrganizer = (project in file("client-organizer")).settings(commonSettings: _*)

lazy val root = Project(id = "shareevent", base = file("."), aggregate =
  Seq(commons,server,clientParticipant,clientOrganizer))
