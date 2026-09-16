# Ktor + SolidJS Template

> [!IMPORTANT]
> This repository is a GitHub mirror. The primary repository, release
> automation, and development history are hosted on
> [GitLab](https://gitlab.com/sedaia-designs/ktor-and-solidjs-template).

A reusable full-stack application starter with a Ktor/Kotlin backend and a
SolidJS 2 frontend. Gradle builds both modules and packages the frontend as
static resources served by Ktor.

To create a project from this template or replace the frontend framework, see
the [usage instructions](../USAGE_INSTRUCTIONS.md).

## Project Links

- [Source repository](https://gitlab.com/sedaia-designs/ktor-and-solidjs-template)
- [Work items](https://gitlab.com/sedaia-designs/ktor-and-solidjs-template/-/issues)
- [Releases](https://gitlab.com/sedaia-designs/ktor-and-solidjs-template/-/releases)

## Features

- Ktor Web API with JSON serialization and centralized error handling
- SolidJS 2 single-page application with client-side routing
- Same-origin API and frontend deployment
- Vite development server with an API proxy to Ktor
- Gradle-managed Node.js and pnpm versions
- Integrated backend tests, frontend tests, linting, and type checking
- Health and readiness endpoints

The backend includes these Ktor components:

| Component | Purpose |
| --- | --- |
| [Static Content](https://start.ktor.io/p/io.ktor/server-static-content) | Serves the built frontend |
| [Status Pages](https://start.ktor.io/p/io.ktor/server-status-pages) | Handles application exceptions |
| [KHealth](https://start.ktor.io/p/dev.hayden/server-khealth) | Provides health and readiness endpoints |
| [Content Negotiation](https://start.ktor.io/p/io.ktor/server-content-negotiation) | Converts request and response content |
| [kotlinx.serialization](https://start.ktor.io/p/io.ktor/server-kotlinx-serialization) | Serializes Kotlin objects as JSON |

## Requirements

- JDK 21

Use the included Gradle wrapper for all root project commands. Gradle downloads
the pinned Node.js and pnpm versions, so separate global installations are not
required for the combined build.

## Quick Start

Build and run the application from the repository root:

```shell
./gradlew run
```

Open <http://localhost:8080>.

## Development

For frontend hot reloading, run the backend and Vite development server in
separate terminals.

Start Ktor from the repository root:

```shell
./gradlew run
```

Start Vite from the frontend module:

```shell
cd src/main/solidjs
pnpm dev
```

Open <http://localhost:3000>. Vite proxies `/api` requests to Ktor at
<http://localhost:8080>.

## Common Commands

Run Gradle commands from the repository root:

| Command | Purpose |
| --- | --- |
| `./gradlew test` | Run Kotlin tests and all frontend verification checks |
| `./gradlew build` | Build the frontend and Ktor application |
| `./gradlew run` | Build the frontend and start Ktor |
| `./gradlew pnpmInstall` | Restore frontend dependencies from the lockfile |
| `./gradlew buildFrontend` | Build only the frontend |
| `./gradlew checkFrontend` | Run frontend tests, linting, and type checking |

Frontend commands can also be run directly from `src/main/solidjs`:

| Command | Purpose |
| --- | --- |
| `pnpm dev` | Start the Vite development server |
| `pnpm build` | Create the production frontend build |
| `pnpm test --run` | Run the frontend tests once |
| `pnpm lint` | Check the frontend with Oxlint |
| `pnpm exec tsc --noEmit` | Check TypeScript types |

## Project Layout

| Path | Purpose |
| --- | --- |
| `src/main/kotlin` | Ktor application, plugins, and API routes |
| `src/main/resources` | Ktor and logging configuration |
| `src/main/solidjs` | SolidJS frontend module |
| `src/main/solidjs/dist/client` | Generated production frontend files |
| `src/test/kotlin` | Ktor integration tests |

During the Gradle build, the generated files from `dist/client` are copied into
the Ktor resources. The packaged application therefore serves the frontend and
API from the same origin.

## Documentation

- [Template usage and customization](../USAGE_INSTRUCTIONS.md)
- [Ktor documentation](https://ktor.io/docs/home.html)
- [SolidJS 2 documentation](https://v2.solidjs.com/)
- [Kotlin community Slack](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up)

## License

This project is available under the [MIT License](../LICENSE).
