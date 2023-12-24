ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val openCvVersion = "1.5.9"
lazy val sttpVersion = "3.3.5"
lazy val nettyVersion = "4.1.82.Final"
lazy val json4sVersion = "3.7.0-M11"

lazy val sttpClientBase = Seq(
  "com.softwaremill.sttp.client3" %% "core" % sttpVersion,
  "com.softwaremill.sttp.client3" %% "json4s" % sttpVersion,
  "com.softwaremill.sttp.client3" %% "okhttp-backend" % sttpVersion,
  "com.softwaremill.sttp.client3" %% "slf4j-backend" % sttpVersion,
  // Force latest netty version
  "io.netty" % "netty-codec-http" % nettyVersion,
  "io.netty" % "netty-codec-socks" % nettyVersion,
  "io.netty" % "netty-handler-proxy" % nettyVersion,
  "io.netty" % "netty-transport-native-epoll" % nettyVersion,
  "io.netty" % "netty-transport-native-kqueue" % nettyVersion)

lazy val sttpClientFuture = sttpClientBase ++ Seq(
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % sttpVersion excludeAll (
    "com.sun.activation" % "jakarta.activation"))

lazy val root = (project in file("."))
  .settings(
    name := "opencv-scala",
    libraryDependencies ++= sttpClientFuture ++ Seq(
      "org.json4s" %% "json4s-native" % json4sVersion,
      "org.bytedeco" % "javacv-platform" % openCvVersion))
