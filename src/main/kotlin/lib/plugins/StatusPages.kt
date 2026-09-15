package org.sedaiadesigns.lib.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

/**
 * Configures the application to handle and respond to exceptions using Ktor's StatusPages plugin.
 *
 * This method installs the StatusPages plugin to intercept uncaught exceptions and respond
 * with a structured HTTP response. By default, it catches all `Throwable` exceptions and
 * responds with a plain-text message indicating an internal server error (HTTP 500) along
 * with the exception message.
 *
 * Features:
 * - Exception handling: Intercepts uncaught errors and responds with a 500 status code.
 * - Custom response: Provides error feedback with exception details in the response body.
 *
 * Recommended usage:
 * - Use this method in combination with other Ktor configuration functions (e.g.,
 *   `configureSerialization`, `configureMonitoring`) to set up core server features.
 */
fun Application.configureStatusPages() {
  install(StatusPages) {
    exception<Throwable> { call, cause ->
      call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
    }
  }
}
