import com.github.gradle.node.pnpm.task.PnpmInstallTask
import com.github.gradle.node.pnpm.task.PnpmTask

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(ktorLibs.plugins.ktor)
  alias(libs.plugins.kotlin.serialization)
  id("com.github.node-gradle.node") version "7.1.0"
}

node {
  version = "22.18.0"
  pnpmVersion = "10.33.2"
  download = true
  nodeProjectDir = file("src/main/solidjs")
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

application {
  mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
  jvmToolchain(21)
}

dependencies {
  implementation(ktorLibs.serialization.kotlinx.json)
  implementation(ktorLibs.server.config.yaml)
  implementation(ktorLibs.server.contentNegotiation)
  implementation(ktorLibs.server.core)
  implementation(ktorLibs.server.netty)
  implementation(ktorLibs.server.statusPages)
  implementation(libs.hayden.khealth)
  implementation(libs.logback.classic)

  testImplementation(kotlin("test"))
  testImplementation(ktorLibs.server.testHost)
}

val solidJsDirectory = layout.projectDirectory.dir("src/main/solidjs")
val solidJsOutput = solidJsDirectory.dir("dist/client")

val pnpmInstall = tasks.named<PnpmInstallTask>("pnpmInstall") {
  args.set(listOf("--frozen-lockfile"))
}

val buildSolidJs = tasks.register<PnpmTask>("buildSolidJs") {
  description = "Builds the SolidJS frontend"
  group = "build"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("build"))

  inputs.files(
    fileTree(solidJsDirectory) {
      exclude("node_modules/**", "dist/**")
    }
  )
  outputs.dir(solidJsOutput)
}

val testSolidJs = tasks.register<PnpmTask>("testSolidJs") {
  description = "Runs the SolidJS test suite"
  group = "verification"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("test", "--run"))
}

val lintSolidJs = tasks.register<PnpmTask>("lintSolidJs") {
  description = "Checks the SolidJS source with Oxlint"
  group = "verification"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("lint"))
}

val typeCheckSolidJs = tasks.register<PnpmTask>("typeCheckSolidJs") {
  description = "Checks the SolidJS TypeScript types"
  group = "verification"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("exec", "tsc", "--noEmit"))
}

val checkSolidJs = tasks.register("checkSolidJs") {
  description = "Runs all SolidJS verification checks"
  group = "verification"

  dependsOn(testSolidJs, lintSolidJs, typeCheckSolidJs)
}

tasks.processResources {
  dependsOn(buildSolidJs)

  from(solidJsOutput) {
    into("static")
  }
}

tasks.test {
  dependsOn(checkSolidJs)
}
