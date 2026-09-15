package org.sedaiadesigns


import io.ktor.server.application.Application
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.sedaiadesigns.lib.configureServerPlugins
import org.sedaiadesigns.routes.apiRoutes
import org.sedaiadesigns.routes.spaRoutes

/**
 * Configures the Ktor application by setting up necessary server plugins and routing.
 *
 * This method initializes the server plugins for status page handling, serialization,
 * and monitoring by invoking the `configureServerPlugins` function. Additionally, it
 * defines the routing structure for the application, setting up API and SPA (Single Page
 * Application) routes.
 *
 * The `configureServerPlugins` function ensures that status pages and error handling
 * are properly configured, JSON serialization is enabled, and health monitoring is set up.
 *
 * The `routing` block contains:
 * - `apiRoutes`: Handles API endpoints for versioned API paths and user operations.
 * - `spaRoutes`: Manages SPA resources, serving the static files and default page.
 */
fun Application.module() {
  configureServerPlugins()
  
  routing {
    route("/api/v1") {
      apiRoutes()
    }
    spaRoutes()
  }
}
