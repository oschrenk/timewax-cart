ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"
ThisBuild / scalacOptions ++= Seq(
  "-deprecation",     // Emit warning for usage of deprecated APIs
  "-encoding",        // Specify character encoding used by source files
  "utf-8",            //   and make that encoding UTF-8
  "-explaintypes",    // Explain type errors in more detail
  "-feature",         // Emit warning for usages of features that should be imported explicitly
  "-unchecked",       // Emit warning where generated code depends on assumptions
  "-Xfatal-warnings", // Fail compilation if there are any warnings.
  "-Xlint:unused"     // Emit warning for unused code
)

ThisBuild / testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

val zioVersion        = "2.1.1"
val tapirVersion      = "1.10.7"
val zioLoggingVersion = "2.2.4"
val zioConfigVersion  = "4.0.2"
val sttpVersion       = "3.9.7"

val dependencies = Seq(
  // config
  "dev.zio" %% "zio-config"          % zioConfigVersion,
  "dev.zio" %% "zio-config-typesafe" % "4.0.2",
  // logging
  "dev.zio"       %% "zio-logging-slf4j" % zioLoggingVersion,
  "dev.zio"       %% "zio-logging"       % zioLoggingVersion,
  "ch.qos.logback" % "logback-classic"   % "1.5.6",
  // tapir
  "com.softwaremill.sttp.tapir"   %% "tapir-zio"              % tapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-zio-http-server"  % tapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-sttp-stub-server" % tapirVersion % "test",
  "com.softwaremill.sttp.tapir"   %% "tapir-sttp-client"      % tapirVersion,
  "com.softwaremill.sttp.tapir"   %% "tapir-json-zio"         % tapirVersion,
  "com.softwaremill.sttp.client3" %% "zio"                    % sttpVersion,
  // test
  "dev.zio" %% "zio-test"     % zioVersion,
  "dev.zio" %% "zio-test-sbt" % zioVersion % "test"
)

lazy val server = (project in file("modules/server"))
  .settings(
    libraryDependencies ++= dependencies
  )

lazy val root = (project in file("."))
  .settings(
    name                      := "timewax-cart",
    Compile / run / mainClass := Some("timewax.Application")
  )
  .aggregate(server)
  .dependsOn(server)
