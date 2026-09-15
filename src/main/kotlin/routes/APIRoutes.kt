package com.example.routes

import com.example.lib.exceptions.InvalidRequestException
import com.example.lib.exceptions.ResourceNotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.apiRoutes() {
  get("/") {
    call.respondText("Hello Ktor!!")
  }
  get("/hello") {
    call.respond(mapOf("hello" to "Ktor"))
  }
  get("/users/{id}") {
    val rawId = call.parameters["id"]
      ?: throw InvalidRequestException("User ID is required")

    val id = rawId.toIntOrNull()
      ?: throw InvalidRequestException("User ID must be an integer")

    // TODO: Replace this in-memory example data with an application data source.
    val users: Array<Map<String, String>> = arrayOf(
      mapOf("name" to "Ada Lovelace", "title" to "First Programmer"),
      mapOf("name" to "Grace Hopper", "title" to "Compiler pioneer"),
      mapOf("name" to "Margaret Hamilton", "title" to "Software engineering")
    )

    val user = users.getOrNull(id)
      ?: throw ResourceNotFoundException("User not found")

    call.respond(user)
  }

  route("{...}") {
    handle {
      call.respond(HttpStatusCode.NotFound)
    }
  }
}
