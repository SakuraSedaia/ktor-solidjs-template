package org.sedaiadesigns.lib

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.sedaiadesigns.lib.plugins.configureMonitoring
import org.sedaiadesigns.lib.plugins.configureSerialization
import org.sedaiadesigns.lib.plugins.configureStatusPages

fun Application.configureServerPlugins() {
  configureStatusPages()
  configureSerialization()
  configureMonitoring()
}