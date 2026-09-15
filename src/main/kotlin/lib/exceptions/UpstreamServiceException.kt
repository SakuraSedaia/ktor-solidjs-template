package com.example.lib.exceptions

/**
 * Indicates that an upstream service failed while processing an application request.
 *
 * @param message sanitized description suitable for an API response.
 * @param cause underlying upstream failure, when available.
 */
class UpstreamServiceException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
