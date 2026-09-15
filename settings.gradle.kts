import org.gradle.api.initialization.resolve.RepositoriesMode

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://jitpack.io")
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
  @Suppress("UnstableApiUsage")
  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

  @Suppress("UnstableApiUsage")
  repositories {
    mavenCentral()
    maven("https://jitpack.io")
    ivy {
      name = "Node.js"
      url = uri("https://nodejs.org/dist")
      patternLayout {
        artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
      }
      metadataSources {
        artifact()
      }
      content {
        includeModule("org.nodejs", "node")
      }
    }
  }
  versionCatalogs {
    create("ktorLibs").from("io.ktor:ktor-version-catalog:3.5.2")
  }
}

rootProject.name = "ktor-solidjs-template"
