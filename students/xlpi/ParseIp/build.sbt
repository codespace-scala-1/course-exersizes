name := "ParseIp"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scodec" %% "scodec-core" % "1.10.2",
  "org.scodec" %% "scodec-bits" % "1.1.0",
  "org.scodec" %% "scodec-protocols" % "1.0.0-M5"
)

## in previous step not compiled sbt with ScalaTestDependencies....it will be demeged...
