package com.example.lib

import io.ktor.server.application.*
import com.example.lib.plugins.configureMonitoring
import com.example.lib.plugins.configureSerialization
import com.example.lib.plugins.configureStatusPages

fun Application.configureServerPlugins() {
  configureStatusPages()
  configureSerialization()
  configureMonitoring()
}
