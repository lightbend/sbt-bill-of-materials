# sbt-bill-of-materials [![bintray-badge](https://api.bintray.com/packages/sbt/sbt-plugin-releases/sbt-bill-of-materials/images/download.svg)](https://bintray.com/sbt/sbt-plugin-releases/sbt-bill-of-materials)

A sbt plugin to generate Bill of Materials (BOM) pom.xml for use with Maven or Gradle.

* [HowToDoInJava: Maven BOM – Bill Of Materials Dependency](https://howtodoinjava.com/maven/maven-bom-bill-of-materials-dependency/)
* [Apache Maven: Bill of Materials (BOM) POMs](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms)

A BOM for a Scala project may either 

1. reference a single Scala minor version and create a BOM POM per Scala minor version, or choose to 
2. list all its artifacts for their Scala versions in a single BOM POM.

Artifacts that typically appear as libraries should use 1. to ensure dependencies do not mix Scala minor versions.

For frameworks which give the user a pre-defined set of dependencies approach 2. may appear simpler. Lagom has been using approach 2. for many years.

## Usage

Add the sbt plug-in in `project/plugins.sbt`

```scala
addSbtPlugin("com.lightbend.sbt" % "sbt-bill-of-materials" % <latest>)
```

### Create a BOM per Scala minor version

Create an sbt (sub-)project which will produce the BOM. list the sbt projects to include in the BOM as `bomIncludeProjects`.

```scala
lazy val billOfMaterials = project
  .enablePlugins(BillOfMaterialsPlugin)
  .settings(
    name := "sample-library-bom",
    bomIncludeProjects := Seq(core, nonScala)
  )
```

Create the BOM pom.xml files with:

```
sbt +billOfMaterials/publishM2
```

### Create a single BOM for all Scala versions

```scala
lazy val billOfMaterials = project
  .enablePlugins(BillOfMaterialsPlugin)
  .settings(
    name := "framework-sample-bom",
    // Just one BOM including all cross Scala versions
    crossVersion := CrossVersion.disabled,
    // Create BOM in the first runn
    crossScalaVersions := crossScalaVersions.value.take(1),
    bomIncludeProjects := Seq(core),
  )
``` 

Create the BOM pom.xml files with:

```
sbt billOfMaterials/publishM2
```

## License

The license is Apache 2.0, see [LICENSE](LICENSE).

## Maintenance notes

The Akka team created this plugin to create Maven BOM POMs for parts of the Akka toolkit.

This project is community-maintained. Pull requests are very welcome –- thanks in advance!

**Lightbend does not offer support for this project.**
