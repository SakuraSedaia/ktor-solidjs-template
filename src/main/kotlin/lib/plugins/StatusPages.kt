package com.example.lib.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.path
import io.ktor.server.response.*
import com.example.lib.exceptions.ErrorResponse
import com.example.lib.exceptions.FeatureNotImplementedException
import com.example.lib.exceptions.InvalidRequestException
import com.example.lib.exceptions.ResourceNotFoundException
import com.example.lib.exceptions.UpstreamServiceException
import kotlin.text.startsWith

/**
 * Configures consistent HTTP error responses using Ktor's StatusPages plugin.
 *
 * API 404 responses and known application exceptions are converted to a serialized
 * [ErrorResponse] containing a stable machine-readable code and a safe client-facing
 * message. Non-API 404 responses retain an empty response body so SPA routing can handle
 * browser-facing paths.
 *
 * Unexpected exceptions are logged with their stack trace for server-side diagnosis. Their
 * details are not exposed to clients; callers receive a generic `internal_error` response
 * with HTTP 500 instead.
 */
fun Application.configureStatusPages() {

  val applicationLog = environment.log

  install(StatusPages) {
    status(HttpStatusCode.NotFound) { call, status ->
      if (call.request.path() == "/api" || call.request.path().startsWith("/api/")) {
        call.respond(status, ErrorResponse("not_found", "API route not found"))
      } else {
        call.respond(status)
      }
    }
    exception<ResourceNotFoundException> { call, cause ->
      call.respond(HttpStatusCode.NotFound, ErrorResponse("not_found", cause.message ?: "Not found"))
    }
    exception<InvalidRequestException> { call, cause ->
      call.respond(HttpStatusCode.BadRequest, ErrorResponse("invalid_request", cause.message ?: "Invalid request"))
    }
    exception<FeatureNotImplementedException> { call, cause ->
      call.respond(
        HttpStatusCode.NotImplemented,
        ErrorResponse("not_implemented", cause.message ?: "Feature not implemented"),
      )
    }
    exception<UpstreamServiceException> { call, cause ->
      call.respond(
        HttpStatusCode.BadGateway,
        ErrorResponse("upstream_error", cause.message ?: "Upstream provider request failed"),
      )
    }
    exception<Throwable> { call, cause ->
      applicationLog.error("Unhandled request failure (${cause::class.qualifiedName})", cause)
      call.respond(
        HttpStatusCode.InternalServerError,
        ErrorResponse("internal_error", "The request could not be completed"),
      )
    }
  }
}
