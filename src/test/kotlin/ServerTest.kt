package com.example

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlin.test.*

class ServerTest {

  @Test
  fun `serves Solid application`() = testApplication {
    application { module() }

    val response = client.get("/")

    assertEquals(HttpStatusCode.OK, response.status)
    assertTrue(response.bodyAsText().contains("<title"))
  }

  @Test
  fun `serves Solid application for client routes`() = testApplication {
    application { module() }

    assertEquals(
      HttpStatusCode.OK,
      client.get("/users/123").status,
    )
  }

  @Test
  fun `serves API independently`() = testApplication {
    application { module() }

    val response = client.get("/api/v1/hello")

    assertEquals(HttpStatusCode.OK, response.status)
    assertEquals("{\"hello\":\"Ktor\"}", response.bodyAsText())
  }

  @Test
  fun `serves user from API`() = testApplication {
    application { module() }

    val response = client.get("/api/v1/users/0")

    assertEquals(HttpStatusCode.OK, response.status)
    assertEquals(
      "{\"name\":\"Ada Lovelace\",\"title\":\"First Programmer\"}",
      response.bodyAsText(),
    )
  }

  @Test
  fun `returns not found for unknown API route`() = testApplication {
    application { module() }

    val response = client.get("/api/v1/missing")

    assertEquals(HttpStatusCode.NotFound, response.status)
    assertEquals(
      "{\"code\":\"not_found\",\"message\":\"API route not found\"}",
      response.bodyAsText(),
    )
  }

  @Test
  fun `returns bad request for malformed or overflowing user IDs`() = testApplication {
    application { module() }

    val malformedResponse = client.get("/api/v1/users/not-a-number")
    val overflowingResponse = client.get("/api/v1/users/999999999999999999999999")

    assertEquals(HttpStatusCode.BadRequest, malformedResponse.status)
    assertEquals(
      "{\"code\":\"invalid_request\",\"message\":\"User ID must be an integer\"}",
      malformedResponse.bodyAsText(),
    )
    assertEquals(HttpStatusCode.BadRequest, overflowingResponse.status)
    assertEquals(
      "{\"code\":\"invalid_request\",\"message\":\"User ID must be an integer\"}",
      overflowingResponse.bodyAsText(),
    )
  }

  @Test
  fun `returns not found for out-of-range user IDs`() = testApplication {
    application { module() }

    val negativeResponse = client.get("/api/v1/users/-1")
    val upperBoundResponse = client.get("/api/v1/users/3")

    assertEquals(HttpStatusCode.NotFound, negativeResponse.status)
    assertEquals(
      "{\"code\":\"not_found\",\"message\":\"User not found\"}",
      negativeResponse.bodyAsText(),
    )
    assertEquals(HttpStatusCode.NotFound, upperBoundResponse.status)
    assertEquals(
      "{\"code\":\"not_found\",\"message\":\"User not found\"}",
      upperBoundResponse.bodyAsText(),
    )
  }

  @Test
  fun `returns a safe response for unexpected server errors`() = testApplication {
    application {
      module()
      routing {
        get("/api/v1/test/internal-error") {
          error("sensitive implementation detail")
        }
      }
    }

    val response = client.get("/api/v1/test/internal-error")
    val responseBody = response.bodyAsText()

    assertEquals(HttpStatusCode.InternalServerError, response.status)
    assertEquals(
      "{\"code\":\"internal_error\",\"message\":\"The request could not be completed\"}",
      responseBody,
    )
    assertFalse(responseBody.contains("sensitive implementation detail"))
  }

  @Test
  fun `exposes health and readiness endpoints`() = testApplication {
    application { module() }

    val healthResponse = client.get("/health")
    val readinessResponse = client.get("/ready")

    assertEquals(HttpStatusCode.OK, healthResponse.status)
    assertEquals("{}", healthResponse.bodyAsText())
    assertEquals(HttpStatusCode.OK, readinessResponse.status)
    assertEquals("{}", readinessResponse.bodyAsText())
  }
}
