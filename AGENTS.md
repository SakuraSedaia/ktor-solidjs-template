# Repository Purpose

This repository is a reusable starting point for building full-stack
applications with a Ktor Web API and a JavaScript frontend. It began as a
personal project for learning Ktor, Kotlin, SolidJS, and their integration, but
the published template is intended for general application development rather
than only as a learning exercise.

The included SolidJS application provides a working foundation for the build,
static-file serving, client routing, and API boundary. Projects created from the
template are expected to extend or replace that example with their own
application features.

Treat changes as template maintenance, not application development:

- Keep the starting application focused, generic, and easy to extend or replace.
- Preserve a working end-to-end example of the frontend calling the Ktor API.
- Avoid product-specific features, branding, credentials, deployment targets,
  or assumptions that would make the template harder to reuse.
- Keep the backend/frontend boundary framework-neutral where practical. SolidJS
  may be replaced by another pnpm-based frontend by following
  `USAGE_INSTRUCTIONS.md`.
- Document setup choices and non-obvious integration constraints so users can
  understand, reuse, and adapt them.

## Agent Change Policy

The repository owner uses maintenance of this template to learn the underlying
Ktor and SolidJS implementation. This is a preference for the owner's workflow,
not a limitation on how end users may use projects created from the template.

Agents must not write or modify application, frontend, build, or configuration
code. For those changes, inspect the repository, explain the relevant concepts,
and give concrete instructions that allow the owner to implement the change.

Agents may directly create or edit:

- tests, because testing is not currently part of the owner's learning
  scope; and
- documentation, including repository guidance and usage instructions.

Do not use those exceptions to make indirect application changes. Agents may
run read-only inspection, builds, tests, linters, type checks, and the application
when needed to diagnose or verify the owner's work. Clearly distinguish a
verified result from a suggested change.

## Project Setup

Before working on the repository:

1. Read this file, `README.md`, and the relevant part of
   `USAGE_INSTRUCTIONS.md`.
2. Inspect `git status` and the relevant diffs. The working tree may contain the
   owner's unfinished work; preserve it and do not clean or rewrite it.
3. Use the Gradle wrapper from the repository root. The project requires JDK 21,
   while Gradle manages the pinned Node.js and pnpm versions. Do not require or
   install a separate global Node.js or pnpm toolchain merely to build the
   template.
4. Keep the current project boundaries in mind:
   - `src/main/kotlin` contains the Ktor application and API;
   - `src/main/resources` contains server configuration;
   - `src/main/solidjs` is the pnpm frontend module;
   - `src/test/kotlin` contains Ktor integration tests; and
   - the frontend production output is `src/main/solidjs/dist/client`, which
     Gradle copies into the Ktor resources.
5. Use `./gradlew pnpmInstall` when frontend dependencies must be restored. Do
   not commit generated directories or files such as `.gradle`, `build`,
   `node_modules`, `dist`, or `file-routes.d.ts`.
6. Establish or verify the baseline with `./gradlew clean test` when appropriate
   for the task. This runs the Kotlin tests plus the frontend tests, lint, and
   TypeScript checks. Use `./gradlew run` only when runtime integration needs to
   be checked.

When helping someone create a project from this template, follow
`USAGE_INSTRUCTIONS.md`. In particular, keep template maintenance separate from
consumer customization: project names, package namespaces, repository URLs, and
framework substitutions belong in the newly created project, not in this source
template unless the user explicitly asks to change the template defaults.

## Git Guidelines

### Staging

- Inspect `git status` and the relevant diffs before staging anything.
- Stage only files that belong to the requested change; never include unrelated user work or generated files.
- Prefer explicit file paths over `git add .` or `git add -A`.
- Review the staged diff with `git diff --staged` before committing.
- Do not discard, overwrite, reset, or otherwise alter existing user changes to produce a clean working tree.

### Commits

- Create a commit only when the user explicitly requests one.
- Keep each commit focused on one cohesive change and ensure its staged contents match its description.
- Use the required format `[Type: module]: Description`.
- Use a short, meaningful module name identifying the affected area, such as `ktor`, `solidjs`, `gradle`, `docs`, or `git`.
- The module may be omitted when the change spans the entire repository, has no meaningful single module, or including it would prevent the message from meeting the length limit. The resulting format is `[Type]: Description`.
- Use an appropriate, consistently capitalized type such as `Add`, `Fix`, `Update`, `Refactor`, `Test`, `Docs`, `Build`, or `Chore`.
- Write the description in the imperative mood, start it with a capital letter, and do not end it with a period.
- Limit the complete commit message to 150 characters, including the prefix, spaces, and punctuation.
- Do not amend, squash, rewrite, or otherwise modify existing commits unless the user explicitly requests it.

Examples:

```text
[Add: solidjs]: Configure the frontend development proxy
[Fix: gradle]: Run pnpm installs in a non-interactive environment
[Docs]: Explain the production build workflow
```

### Pushing

- Push only when the user explicitly requests it.
- Before pushing, verify the current branch, configured remote, intended upstream, and commits that will be sent.
- Use a normal push by default; never force-push unless the user explicitly requests it and the exact target has been verified.
- Report the destination remote and branch after a successful push, or clearly report any failure without repeatedly retrying unsafe alternatives.

## SolidJS Agent Guide

for [./src/main/solidjs/]

This is a SolidJS 2.x project. Solid is not React: components run once (there is no re-render), reactivity is fine-grained through signals, and effects/memos have Solid-specific semantics. Do not port React patterns.

### Versioned skills (in node_modules — read on demand)

The installed packages ship agent skills that match their exact installed versions:

- `node_modules/solid-js/skills/reactivity-diagnostics/SKILL.md` — repair guide mapping every dev-mode diagnostic code (e.g. `REACTIVE_WRITE_IN_OWNED_SCOPE`, `STRICT_READ_UNTRACKED`) to its prescribed fix. Read it whenever a Solid diagnostic code appears in test output or the browser console.
- `node_modules/@solidjs/diagnostics/skills/agent-loops/SKILL.md` — how to capture reactive evidence (which scopes re-ran and why, wasted recomputes, cost tables) and assert budgets, in tests and against live pages.

### Reactive diagnostics — capture evidence instead of guessing

Use these whenever you are debugging reactivity (something doesn't update, updates too often, or is slow) or verifying a change didn't regress update granularity:

- **In tests:** `captureArtifact()` from `@solidjs/diagnostics` wraps a scenario and returns a serializable artifact of diagnostics + rerun attribution; matchers from `@solidjs/diagnostics/vitest` (`toHaveNoDiagnostics`, `toStayWithinRerunBudget`, `toHaveNoWaste`, …) assert on it. No browser needed.
- **Against the running dev server** (`diagnostics: true` in vite.config.ts; dev-only, no-op in builds). Requires an open page connected to the dev server (e.g. via a browser tool):
    - `GET /__solid/diagnostics` — status and connected client count
    - `POST /__solid/diagnostics` with JSON `{"method":"begin"}` then `{"method":"end"}` — capture a session into an artifact
    - `{"method":"whyDidRun","params":{"name":"<scope name>"}}` — recorded re-runs of one named scope in the open session
    - `{"method":"costs"}` — running cost tables for the open session

Name your signals/memos/effects (the `{ name: "..." }` option) — attribution reports scopes by name.
