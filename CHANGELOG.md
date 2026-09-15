# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2026-09-15

### Added

- Ktor backend with JSON content negotiation, status pages, request monitoring,
  static frontend hosting, and health endpoints.
- SolidJS frontend with file-based routing, metadata support, and reusable API
  fetching utilities.
- Example user API and frontend routes demonstrating end-to-end integration.
- Backend integration tests covering application routes, API errors, user ID
  validation, safe server-error responses, and health endpoints.
- Frontend component and routing tests using Vitest and Solid Testing Library.
- Gradle-managed Node.js and pnpm installations for reproducible builds.
- Gradle lifecycle integration for the frontend build, tests, linting, and
  TypeScript type checking.
- Template setup documentation, including instructions for replacing the
  `com.example` group and package namespace.
- MIT License covering the complete project.

### Changed

- Standardized example user IDs as zero-based values across the backend and
  frontend.
- Adopted a neutral `com.example` identity for downstream template users.

### Fixed

- Return structured client errors for malformed, negative, out-of-range, and
  overflowing user IDs.
- Return safe JSON responses for unhandled server errors without exposing
  internal exception details.
- Return `404 Not Found` for unknown API routes.

[Unreleased]: https://gitlab.com/sedaia-designs/ktor-and-solidjs-template/-/compare/v1.0.0...main
[1.0.0]: https://gitlab.com/sedaia-designs/ktor-and-solidjs-template/-/releases/v1.0.0
