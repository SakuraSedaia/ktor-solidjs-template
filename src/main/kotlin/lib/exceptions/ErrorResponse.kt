package com.example.lib.exceptions

import kotlinx.serialization.Serializable

/**
 * Stable error envelope returned by API endpoints.
 *
 * @param code machine-readable error category.
 * @param message sanitized user-facing error description.
 */
@Serializable
data class ErrorResponse(val code: String, val message: String)
