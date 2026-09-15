package org.sedaiadesigns

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.*

class ServerTest {
  
  @Test
  fun `test root endpoint`() = testApplication {
    application { module() }
    // verify server root returns 200
    assertEquals(HttpStatusCode.OK, client.get("/").status)
  }
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
}
