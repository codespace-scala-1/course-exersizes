import Dependencies._

lazy val calculator  = (project in file("calculator")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1"
    )),
    name := "Calculator",
    libraryDependencies += scalaTest % Test
  )


lazy val life  = (project in file("life")).
   settings(
    name := "Life",
    libraryDependencies += scalaTest % Test
   )
