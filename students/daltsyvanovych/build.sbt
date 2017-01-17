import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.1",
  libraryDependencies += scalaTest % Test
)

lazy val complex = project.in(file("complex")).
  settings(commonSettings: _*).
  settings(
    name := "Complex numbers"
  )

lazy val calculator = project.in(file("calculator")).
  settings(commonSettings: _*).
  settings(
    name := "Calculator"
  )

lazy val life = project.in(file("life")).
  settings(commonSettings: _*).
  settings(
    name := "Game of life"
  )

lazy val simpleTasks = project.in(file("simple")).
  settings(commonSettings: _*).
  settings(
    name := "Simple functions"
  )

lazy val root = (project in file(".")).
  aggregate(complex, calculator, life)