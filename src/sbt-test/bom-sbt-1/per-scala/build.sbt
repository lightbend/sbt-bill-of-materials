import sbt._
import sbt.Keys._

val Scala212 = "2.12.11"

val commonSettings =
  Seq(organization := "com.lightbend", version := "4.3.2.1", crossScalaVersions := Seq("2.13.4", Scala212))

lazy val core =
  project.settings(commonSettings).settings(name := "scripted-core")

lazy val testingPlugin =
  project
    .settings(commonSettings)
    .settings(
      name := "testing-plugin",
      crossScalaVersions := Seq(Scala212),
      description := "A hypothetical sbt plugin which is not cross-compiled thus doesn't show in the Scala 2.13 BOM."
    )

lazy val nonScala =
  project
    .settings(commonSettings)
    .settings(
      name := "scripted-java",
      crossVersion := CrossVersion.disabled,
      description := "A Java only project, without the Scala minor postfix in the artifact ID."
    )

lazy val billOfMaterials = project
  .enablePlugins(BillOfMaterialsPlugin)
  .settings(commonSettings)
  .settings(
    name := "sample-library-bom",
    bomIncludeProjects := Seq(core, testingPlugin, nonScala),
    // added for testing only
    publishM2Configuration := publishM2Configuration.value.withOverwrite(true)
  )
