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
version = "1.0.0"

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

val frontendDirectory = layout.projectDirectory.dir("src/main/solidjs")
val frontendOutput = frontendDirectory.dir("dist/client")

val pnpmInstall = tasks.named<PnpmInstallTask>("pnpmInstall") {
  args.set(listOf("--frozen-lockfile"))
}

val buildFrontend = tasks.register<PnpmTask>("buildFrontend") {
  description = "Builds the frontend"
  group = "build"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("build"))

  inputs.files(
    fileTree(frontendDirectory) {
      exclude("node_modules/**", "dist/**")
    }
  )
  outputs.dir(frontendOutput)
}

val testFrontend = tasks.register<PnpmTask>("testFrontend") {
  description = "Runs the Frontend test suite"
  group = "verification"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("test", "--run"))
}

val lintFrontend = tasks.register<PnpmTask>("lintFrontend") {
  description = "Checks the Frontend source with Oxlint"
  group = "verification"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("lint"))
}

val typeCheckFrontend = tasks.register<PnpmTask>("typeCheckFrontend") {
  description = "Checks the Frontend TypeScript types"
  group = "verification"

  dependsOn(pnpmInstall)
  environment.put("CI", "true")
  args.set(listOf("exec", "tsc", "--noEmit"))
}

val checkFrontend = tasks.register("checkFrontend") {
  description = "Runs all Frontend verification checks"
  group = "verification"

  dependsOn(testFrontend, lintFrontend, typeCheckFrontend)
}

tasks.processResources {
  dependsOn(buildFrontend)

  from(frontendOutput) {
    into("static")
  }
}

tasks.test {
  dependsOn(checkFrontend)
}
