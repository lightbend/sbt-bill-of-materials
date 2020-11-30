import sbt._
import sbt.Keys._

val commonSettings =
  Seq(organization := "com.lightbend", version := "4.3.2.1", crossScalaVersions := Seq("2.13.4", "2.12.11"))

lazy val core =
  project.settings(commonSettings).settings(name := "scripted-core", crossScalaVersions := Seq("2.13.4", "2.12.11"))

lazy val nonScala =
  project.settings(commonSettings).settings(name := "scripted-java", crossVersion := CrossVersion.disabled)

lazy val billOfMaterials = project
  .enablePlugins(BillOfMaterialsPlugin)
  .settings(commonSettings)
  .settings(
    name := "sample-library-bom",
    bomIncludeProjects := Seq(core, nonScala),
    // added for testing only
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true)
  )
