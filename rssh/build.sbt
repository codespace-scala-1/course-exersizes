import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.1",
  libraryDependencies += scalaTest % Test
)


lazy val calculator  = (project in file("calculator")).
  settings(commonSettings: _*).
  settings(
    name := "Calculator"
  )


lazy val life  = (project in file("life")).
   settings(commonSettings: _*).
   settings(
    name := "Life"
   )

lazy val root = (project in file(".")).
  aggregate(calculator, life)

