package org.sedaiadesigns.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import kotlin.text.toInt

fun Route.apiRoutes() {
   get("/") {
     call.respondText("Hello Ktor!!")
   }
  get("/hello") {
    call.respond(mapOf("hello" to "Ktor"))
  }
   get("/users/{id}") {
     val id = call.parameters["id"] ?: "-1"
     
     if (id == "-1") {
       call.respond(HttpStatusCode.NotFound)
     }
     
     // TODO: Change this to pull JSON direct from the associated users.json in a database.
     val tempMap: Array<Map<String, String>> = arrayOf(
       mapOf("name" to "Ada Lovelace", "title" to "First Programmer"),
       mapOf("name" to "Grace Hopper", "title" to "Compiler pioneer"),
       mapOf("name" to "Margaret Hamilton", "title" to "Software engineering")
     )
     
     // The Minus one is to offset the fact that the Frontend-provided ID's start at 1, while the tempMap stores values at base 10
     call.respond(tempMap[id.toInt().minus(1)])
   }
}
