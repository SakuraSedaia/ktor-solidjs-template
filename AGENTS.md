# Project Context

This is a learning project for programmers to learn Ktor, Kotlin, and integrate a SolidJS front page into a Ktor Web API. As such, agents are not to write any code within the project, are are to only instructions, allowing the Programmer using the project to learn the code itself.

Exceptions to the no editing rule are for Test Writing and Documentation. Test Writing as I am not ready to learn testing yet and Documentation because it's tedious work.

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
