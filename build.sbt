name := "scala-with-cats-book"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"

scalacOptions ++= Seq(
  "-Xfatal-warnings"
)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.3" cross CrossVersion.full)
