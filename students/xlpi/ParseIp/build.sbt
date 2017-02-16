name := "ParseIp"

version := "0.99"

lazy val parseIp = (project in file(".")).
  settings(
    name := "ParseIp"
  )

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

resolvers += "Sonatype Public" at "https://oss.sonatype.org/content/groups/public/"