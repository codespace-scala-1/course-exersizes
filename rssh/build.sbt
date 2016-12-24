import Dependencies._

lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.1",
  libraryDependencies += scalaTest % Test
)


lazy val calculator  = (project in file("calculator")).
  settings(commonSettings: _*).
  settings(
    name := "Calculator",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
  )


lazy val life  = (project in file("life")).
   settings(commonSettings: _*).
   settings(
    name := "Life"
   )

lazy val dscopeMacro1  = (project in file("dscope1-macro")).
   settings(commonSettings: _*).
   settings(
    name := "dscope1-macro",
    libraryDependencies += scalaVersion("org.scala-lang" % "scala-reflect" % _ ).value
   )

lazy val root = (project in file(".")).
  aggregate(calculator, life, dscopeMacro1)

