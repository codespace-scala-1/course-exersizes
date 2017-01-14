import Dependencies._

lazy val commonSettings = Seq( organization := "com.example", scalaVersion in ThisBuild := "2.12.1", libraryDependencies += scalaTest % Test )

lazy val complex = project.in(file("complex")). settings(commonSettings: _*). settings( name := "Complex numbers" )

lazy val calculator = project.in(file("calculator")). settings(commonSettings: _*). settings( name := "Calculator" )

lazy val life = project.in(file("life")). settings(commonSettings: _*). settings( name := "Game of life" )

lazy val simpleTasks = project.in(file("simple")). settings(commonSettings: _*). settings( name := "Simple functions" )

lazy val calculationParser = project.in(file("parser")). settings(commonSettings: _*). settings( name := "Expression parser").dependsOn(calculator)

lazy val ticTac = project.in(file("parser")). settings(commonSettings: _*). settings( name := "Tic tac game" ).dependsOn(calculator)

lazy val root = (project in file(".")). aggregate(complex, calculator, life, simpleTasks, calculationParser)
