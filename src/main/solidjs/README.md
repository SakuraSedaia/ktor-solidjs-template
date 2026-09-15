# SolidJS Frontend

This directory contains the SolidJS frontend for the Ktor application. It is part of the root Gradle build rather than a separately deployed application.

## Requirements

- Node.js 22.18 or newer
- pnpm 10.33.2 or newer within the 10.x release line

Install the dependencies from this directory with the committed lockfile:

```shell
pnpm install --frozen-lockfile
```

## Development

Run the Ktor backend from the repository root:

```shell
./gradlew run
```

In a second terminal, start Vite from this directory:

```shell
pnpm dev
```

Open <http://localhost:3000>. Vite proxies requests under `/api` to the backend at <http://localhost:8080>.

## Commands

| Command                  | Description                                  |
|--------------------------|----------------------------------------------|
| `pnpm dev`               | Start the Vite development server            |
| `pnpm build`             | Create the production frontend build         |
| `pnpm serve`             | Preview the production frontend build        |
| `pnpm test --run`        | Run the component tests once                 |
| `pnpm lint`              | Check source files with Oxlint                |
| `pnpm exec tsc --noEmit` | Check TypeScript types without emitting files |

The production client files are generated in `dist/client`. The root Gradle build copies them into the Ktor application automatically; do not commit `dist` or deploy it separately when using the combined template.

## Structure

- `src/routes` defines file-system routes.
- `src/components` contains reusable UI components and their tests.
- `src/utils` contains shared frontend utilities.
- `vite.config.ts` configures SolidJS, routing, tests, and the development API proxy.

The complete repository, including this frontend, is licensed under the root [MIT License](../../../LICENSE).
