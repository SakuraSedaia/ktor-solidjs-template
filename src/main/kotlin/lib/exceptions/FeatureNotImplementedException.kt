package com.example.lib.exceptions

/**
 * Indicates that a recognized application capability has not been implemented.
 *
 * @param message sanitized description suitable for an API response.
 */
class FeatureNotImplementedException(message: String) : RuntimeException(message)
