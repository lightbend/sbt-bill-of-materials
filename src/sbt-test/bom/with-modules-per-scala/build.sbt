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

lazy val billOfMaterials = project
  .enablePlugins(BillOfMaterialsPlugin)
  .settings(
    name := "framework-sample-bom",
    bomIncludeModules := Seq("com.typesafe.akka" %% "akka-actor-typed" % "2.6.13"),
    crossVersion := CrossVersion.disabled,
    // added for testing only
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true)
  )
