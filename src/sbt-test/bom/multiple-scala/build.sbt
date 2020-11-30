import sbt._
import sbt.Keys._

inThisBuild(
  Def.settings(
    crossScalaVersions := Seq("2.13.4", "2.12.11"),
    scalaVersion := (ThisBuild / crossScalaVersions).value.head,
    organization := "com.lightbend",
    version := "4.3.2.1"
  )
)

lazy val core =
  project.settings(name := "scripted-core")

lazy val billOfMaterials = project
  .enablePlugins(BillOfMaterialsPlugin)
  .settings(
    name := "framework-sample-bom",
    // Just one BOM including all cross Scala versions
    crossVersion := CrossVersion.disabled,
    // Create BOM in the 2.13 run
    crossScalaVersions := crossScalaVersions.value.take(1),
    bomIncludeProjects := Seq(core),
    // added for testing only
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true)
  )
