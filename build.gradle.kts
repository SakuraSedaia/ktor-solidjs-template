plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(ktorLibs.plugins.ktor)
  alias(libs.plugins.kotlin.serialization)
}

group = "org.sedaiadesigns"
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

tasks.processResources {
  dependsOn(buildSolidJs)
  
  from(solidJsOutput) {
    into("static")
  }
}


val pnpmInstall by tasks.registering(Exec::class) {
  description = "Installs the SolidJS Dependencies"
  group = "build"
  
  workingDir(solidJsDirectory)
  environment("CI", "true")
  commandLine("/Users/Sakura/Library/pnpm/pnpm", "install", "--frozen-lockfile")
  
  inputs.files(
    solidJsDirectory.file("package.json"),
    solidJsDirectory.file("pnpm-lock.yaml"),
    solidJsDirectory.file("pnpm-workspace.yaml"),
  )
  outputs.dir(solidJsDirectory.dir("node_modules"))
}

val buildSolidJs by tasks.registering(Exec::class) {
  description = "Builds the SolidJS frontend"
  group = "build"
  
  dependsOn(pnpmInstall)
  workingDir(solidJsDirectory)
  environment("CI", "true")
  commandLine("/Users/Sakura/Library/pnpm/pnpm", "build")
  
  inputs.files(
    fileTree(solidJsDirectory) {
      exclude("node_modules/**", "dist/**")
    }
  )
  outputs.dir(solidJsOutput)
}