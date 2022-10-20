scalaVersion := "2.12.17"

sbtPlugin := true

enablePlugins(SbtPlugin)
import scala.collection.JavaConverters._
scriptedLaunchOpts += s"-Dproject.version=${version.value}"
scriptedLaunchOpts ++= java.lang.management.ManagementFactory.getRuntimeMXBean.getInputArguments.asScala.filter(a =>
  Seq("-Xmx", "-Xms", "-XX", "-Dfile").exists(a.startsWith)
)
scriptedBufferLog := false

crossSbtVersions := List("1.1.0")
organization := "com.lightbend.sbt"
name := "sbt-bill-of-materials"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage := Some(url("https://github.com/lightbend/sbt-bill-of-materials"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/lightbend/sbt-bill-of-materials"),
    "git@github.com:lightbend/sbt-bill-of-materials.git"
  )
)
developers += Developer(
  "contributors",
  "Contributors",
  "info@lightbend.com",
  url("https://github.com/lightbend/sbt-bill-of-materials/graphs/contributors")
)
organizationName := "Lightbend Inc."
organizationHomepage := Some(url("https://www.lightbend.com"))
startYear := Some(2020)
description := "Create 'Bill of Materials' (BOM) POM files from sbt for consumption in Maven and Gradle."

// no API docs
Compile / doc / sources := Seq.empty

sonatypeProfileName := "com.lightbend"
publishTo := sonatypePublishToBundle.value
publishMavenStyle := true

enablePlugins(AutomateHeaderPlugin)
scalafmtOnCompile := true
