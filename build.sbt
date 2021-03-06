import bintray.Keys._

name := "akka-http-metrics"

organization := "backline"

version := "0.2.0"

scalaVersion := "2.11.7"

crossScalaVersions ++= Seq("2.10.5", "2.11.7")

resolvers ++= Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "io.dropwizard.metrics" % "metrics-core" % "3.1.2",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0"
)

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
)

scalacOptions in Test ++= Seq("-Yrangepos")

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.6.2" % "test",
  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0" % "test"
)

bintrayOrganization in bintray := Some("backline")

bintrayPublishSettings ++ Seq(
  publishArtifact in Test := false,
  repository in bintray := "open-source",
  homepage := Some(url("https://github.com/backline/akka-http-metrics")),
  licenses ++= Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html")))
)
