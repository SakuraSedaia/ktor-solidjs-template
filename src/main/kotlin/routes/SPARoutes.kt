package com.example.routes

import io.ktor.server.http.content.singlePageApplication
import io.ktor.server.routing.Route

fun Route.spaRoutes() {
  singlePageApplication {
    useResources = true
    filesPath = "static"
    defaultPage = "index.html"
  }
}
