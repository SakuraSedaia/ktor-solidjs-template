package org.sedaiadesigns.lib.plugins

import io.ktor.server.application.*
import dev.hayden.KHealth

/**
 * Configures health check monitoring for the application using the KHealth plugin.
 *
 * This method installs the KHealth plugin, enabling the application to expose
 * health check endpoints for monitoring and operational status reporting.
 *
 * Features:
 * - Provides predefined health check endpoints to monitor application status.
 * - Enhances observability and facilitates integration with monitoring tools.
 *
 * Recommended usage:
 * - Use this method alongside other configuration functions such as `configureStatusPages`
 *   and `configureSerialization` to establish comprehensive server functionality.
 */
fun Application.configureMonitoring() {
  install(KHealth)
}