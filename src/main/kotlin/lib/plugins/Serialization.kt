package org.sedaiadesigns.lib.plugins

import io.ktor.server.application.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

/**
 * Configures JSON serialization for the application by installing Ktor's ContentNegotiation plugin.
 *
 * This method enables the application to automatically handle JSON serialization and deserialization,
 * allowing seamless processing of JSON data in API requests and responses.
 *
 * Features:
 * - Installs the ContentNegotiation plugin to facilitate media-type negotiation.
 * - Configures the plugin to use Kotlinx serialization for JSON data.
 *
 * Recommended usage:
 * - Use this method to configure JSON support as part of the server setup process.
 * - Combine with other configuration functions, such as `configureStatusPages` and `configureMonitoring`,
 *   to build a fully functional application.
 */
fun Application.configureSerialization() {
  install(ContentNegotiation) {
    json()
  }
}