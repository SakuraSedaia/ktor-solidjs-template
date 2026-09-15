package com.example.lib.exceptions

/**
 * Indicates that a requested application resource does not exist.
 *
 * @param message sanitized description suitable for an API response.
 */
class ResourceNotFoundException(message: String) : RuntimeException(message)
