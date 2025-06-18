ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "spark-structure-streaming",
    idePackagePrefix := Some("org.keiron.libraries")
  )
