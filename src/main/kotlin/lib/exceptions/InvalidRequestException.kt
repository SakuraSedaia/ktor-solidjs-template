package com.example.lib.exceptions

/**
 * Indicates that caller input failed application-level validation.
 *
 * @param message sanitized description suitable for an API response.
 */
class InvalidRequestException(message: String) : RuntimeException(message)
