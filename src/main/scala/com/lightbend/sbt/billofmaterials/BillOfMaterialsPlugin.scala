/*
 * Copyright 2020 Lightbend Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightbend.sbt.billofmaterials

import sbt.Keys._
import sbt.{ AutoPlugin, PluginTrigger, _ }

import scala.xml.{ Comment, Node }

/**
 * Plugin to create a Maven Bill of Materials (BOM) pom.xml
 *
 * Set `crossVersion := CrossVersion.disabled` to create a single BOM with all Scala binary versions.
 *
 * - https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms
 * - https://howtodoinjava.com/maven/maven-bom-bill-of-materials-dependency/
 */
object BillOfMaterialsPlugin extends AutoPlugin {

  override def trigger = PluginTrigger.NoTrigger

  object autoImport extends BillOfMaterialsPluginKeys
  import autoImport._

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      // publish Maven Style
      publishMavenStyle := true,
      autoScalaLibrary := false,
      bomIncludeProjects := Seq.empty[ProjectReference],
      bomIncludeModules := Seq.empty[ModuleID],
      bomDependenciesListing := {
        val dependencies =
          Def.settingDyn {
            val multipleScalaVersionsInBom = crossVersion.value == CrossVersion.disabled
            val desiredScalaBinaryVersion = CrossVersion.binaryScalaVersion(scalaVersion.value)
            ((bomIncludeProjects.value).map { project =>
              Def.setting {
                val module = ModuleID(
                  organization = (project / organization).value,
                  name = (project / artifact).value.name,
                  revision = (project / version).value
                ).withCrossVersion((project / crossVersion).value)

                toXml(
                  module,
                  multipleScalaVersionsInBom,
                  desiredScalaBinaryVersion,
                  moduleScalaVersion = Option((project / scalaVersion).value),
                  crossScalaVersions = (project / crossScalaVersions).value
                )
              }
            } ++
            (bomIncludeModules.value).map { moduleId =>
              Def.setting {
                toXml(
                  moduleId,
                  multipleScalaVersionsInBom,
                  desiredScalaBinaryVersion,
                  moduleScalaVersion = None,
                  crossScalaVersions = crossScalaVersions.value
                )
              }
            }).join
          }.value

        // format: off
        <dependencyManagement>
          <dependencies>
            {dependencies}
          </dependencies>
        </dependencyManagement>
        // format:on
      },
      pomExtra := (pomExtra.value) :+ bomDependenciesListing.value
    ) ++
      // This disables creating jar, source jar and javadocs, and will cause the packaging type to be "pom" when the pom is created
      Classpaths.defaultPackageKeys.map(_ / publishArtifact := false)

  private def toXmlIfDesiredVersion(artifactName: String, organization: String, version: String, scalaVersion: String, desiredScalaBinaryVersion: String): Node = {
    if (CrossVersion.binaryScalaVersion(scalaVersion) == desiredScalaBinaryVersion) {
      toXmlScalaBinary(artifactName, organization, version, scalaVersion)
    } else Comment(s" $artifactName is not available for Scala $desiredScalaBinaryVersion ")
  }

  private def toXmlScalaBinary(artifactName: String, organization: String, version: String, scalaVersion: String) = {
    val crossFunc = CrossVersion(Binary(), scalaVersion, CrossVersion.binaryScalaVersion(scalaVersion)).get
    // convert artifactName to match the desired scala version
    val artifactId = crossFunc(artifactName)
    toXml(artifactId, organization, version)
  }

  private def toXml(
                    module: ModuleID,
                    multipleScalaVersionsInBom: Boolean,
                    desiredScalaBinaryVersion: String,
                    moduleScalaVersion: Option[String],
                    crossScalaVersions: Seq[String]): Seq[Node] = {
    import module._
    if (crossVersion == CrossVersion.disabled) {
      toXml(name, organization, revision)
    } else if (crossVersion == CrossVersion.binary) {
      if (multipleScalaVersionsInBom) {
        crossScalaVersions.map(scalaV => toXmlScalaBinary(name, organization, revision, scalaV))
      } else {
        moduleScalaVersion
          .map(scalaV => toXmlIfDesiredVersion(name, organization, revision, scalaV, desiredScalaBinaryVersion))
          .getOrElse(toXmlScalaBinary(name, organization, revision, desiredScalaBinaryVersion))
      }
    } else {
      throw new RuntimeException(s"Support for `crossVersion := $crossVersion` is not implemented")
    }
  }

  private def toXml(artifactId: String, organization: String, version: String): Node = {
    <dependency>
      <groupId>{organization}</groupId>
      <artifactId>{artifactId}</artifactId>
      <version>{version}</version>
    </dependency>
  }
}
