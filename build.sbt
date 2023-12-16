ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "opencv-scala",
    libraryDependencies += "org.bytedeco" % "javacv-platform" % "1.5.9"
  )
