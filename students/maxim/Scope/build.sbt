name := "Scope"
version := "0.0.1"
organization := "com.codespace"

scalaVersion := "2.12.1"
mainClass in(Compile, run) := Some("com.codespace.maxim.dscope.Main")


scalaVersion in ThisBuild := "2.12.1"
run <<= run in Compile in core

lazy val macros = (project in file("macros")).settings(
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

lazy val core = (project in file("core")) dependsOn macros
