package org.sedaiadesigns.lib

data class ApplicationDependencies (
  val string: String
)

fun createDependencies(): ApplicationDependencies = ApplicationDependencies(
  string = ""
)