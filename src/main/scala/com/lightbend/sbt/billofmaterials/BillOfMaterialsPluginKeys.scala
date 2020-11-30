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

import sbt._

import scala.xml.Elem

trait BillOfMaterialsPluginKeys {
  val bomIncludeProjects =
    settingKey[Seq[ProjectReference]]("the list of projects to include in the Bill of Materials pom.xml")
  val bomDependenciesListing =
    settingKey[Elem]("the generated `<dependencyManagement>` section to be added to `sbt.pomExtra`")
}

object BillOfMaterialsPluginKeys extends BillOfMaterialsPluginKeys
