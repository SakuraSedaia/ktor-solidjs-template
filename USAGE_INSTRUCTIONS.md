# Ktor + SolidJS Template

This project was initially created using the [Ktor Project Generator](https://start.ktor.io),
and built up to include an embedded SolidJS V2 Frontend.

Here are some useful links to get you started:

* [Ktor Documentation](https://ktor.io/docs/home.html)
* [Ktor GitHub page](https://github.com/ktorio/ktor)
* [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). [Request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up).
* [SolidJS V2 Documentation](https://v2.solidjs.com/)

## How to Use This Template

Choose either the GitLab or GitHub workflow below. Both create an independent
project that you can modify without affecting this template.

### GitLab

GitLab does not provide a GitHub-style **Use this template** button for this
repository, so create an independent project manually.

1. Clone the template into a directory named for your project:

   ```shell
   git clone --depth 1 \
     https://gitlab.com/sedaia-designs/ktor-and-solidjs-template.git \
     your-project-name
   cd your-project-name
   ```

   The shallow clone downloads only the current template revision and the
   minimal history needed to check it out.

   In IntelliJ IDEA, you can instead select **File > New > Project from Version
   Control**, enter the HTTPS URL, choose your project directory, and enable
   **Shallow clone** with a depth of `1`.

2. Remove the template's Git metadata and initialize a new repository:

   ```shell
   rm -rf .git
   git init --initial-branch=main
   git add .
   git commit -m "Initial commit"
   ```

   Removing `.git` disconnects the new project from the template's history and
   configured remotes. On Windows PowerShell, use the following command instead
   of `rm -rf .git`:

   ```powershell
   Remove-Item -Recurse -Force .git
   ```

3. Create a new **blank** repository on your preferred hosting platform. When
   using GitLab, leave **Initialize repository with a README** disabled so the
   remote does not begin with a conflicting commit.

4. Connect and push your new repository, replacing the example URL with the URL
   of the blank repository:

   ```shell
   git remote add origin https://gitlab.com/your-namespace/your-project-name.git
   git push --set-upstream origin main
   ```

### GitHub

The GitHub mirror is configured as a template repository, so GitHub can create
an independent repository without retaining the template's commit history.

1. Open the template repository on GitHub.
2. Select **Use this template > Create a new repository**.
3. Choose an owner, repository name, description, and visibility, then select
   **Create repository**.
4. Clone the newly created repository using the URL shown by GitHub:

   ```shell
   git clone https://github.com/your-account/your-project-name.git
   cd your-project-name
   ```

### Customize Your Project

After creating the repository:

1. Replace the `com.example` Gradle group and Kotlin package namespace by
   following [Customize the Project Group](#customize-the-project-group).
2. Change `rootProject.name` in `settings.gradle.kts` and the frontend package
   name, description, repository, and homepage in
   `src/main/solidjs/package.json`.
3. Replace template-specific names, links, release details, and badges in the
   README and changelog.
4. Review the MIT License copyright notice and update it for the new project.
5. Verify the customized project:

   ```shell
   ./gradlew clean test
   ```

#### Project Group

The template uses `com.example` as a neutral placeholder for both the Gradle group and Kotlin package namespace. Replace it before starting a new project. A reverse-domain name that you control is conventional, such as `com.yourcompany` or `dev.yourname`.

In IntelliJ IDEA, use **Refactor > Rename** on the `com.example` package so package declarations and imports are updated together. Then replace the remaining `com.example` values in:

- `build.gradle.kts`, where it defines the Gradle `group`.
- `src/main/resources/application.yaml`, where it identifies the Ktor application module.
- `src/test/kotlin`, so tests remain in the same package as the application entry point.

Search the entire project for `com.example` afterward to confirm that no placeholder references remain. Finally, run:

```shell
./gradlew clean test
```

The build will fail to start if the module name in `application.yaml` does not exactly match the package containing `Application.kt`.

#### Project Name

To change the name of the project internally, edit the following values:
- `rootProject.name` in the [settings.gradle.kts](settings.gradle.kts), line 43
- "name" in the [package.json](src/main/solidjs/package.json)

#### Swap the Frontend Framework

The Ktor application is not tied to SolidJS. It serves the static frontend files
produced by the package build, so you can replace SolidJS with another
Vite-compatible frontend such as React, Vue, or Svelte without rewriting the
backend.

For a simple framework swap, recreate the small starter application that is
already present before building application-specific features. This first step
proves that the replacement framework can build, call the Ktor API, and handle
client-side routes. Recreate the behavior using the new framework's own patterns
rather than translating SolidJS internals or preserving its exact reactive
implementation.

To make a simple framework swap:

1. Generate a minimal TypeScript project for the chosen framework in a temporary
   directory. Continue to use pnpm, because the root Gradle build manages pnpm
   and invokes it automatically.
2. Rename the `src/main/solidjs` module directory for the chosen framework, such
   as `src/main/react`, `src/main/vue`, or `src/main/svelte`.
3. Update the two directory references in `build.gradle.kts` to use the renamed
   module directory. For example, when renaming it to `src/main/react`, change:

   ```kotlin
   nodeProjectDir = file("src/main/react")
   val frontendDirectory = layout.projectDirectory.dir("src/main/react")
   ```

   The Gradle tasks already use framework-neutral names such as
   `buildFrontend`, `testFrontend`, and `checkFrontend`, so they do not need to
   be renamed.
4. Replace the contents of the renamed module directory with the generated
   project.
5. Configure the frontend development server to proxy `/api` requests to
   `http://localhost:8080`, as the existing Vite configuration does.
6. Recreate the current proof-of-function behavior in the new framework:
   - a home page that requests `/api/v1/hello`;
   - a working counter component;
   - a `/users/:id` route that requests `/api/v1/users/:id`;
   - a client-side not-found page; and
   - at least one component test.
7. Keep compatible `build`, `test`, and `lint` scripts in `package.json`. Gradle
   currently runs `pnpm build`, `pnpm test --run`, `pnpm lint`, and
   `pnpm exec tsc --noEmit`.
8. Make the production build emit an `index.html` and its assets into
   `dist/client`. If the framework normally emits to `dist`, either change its
   output directory or update the frontend output path in `build.gradle.kts`.
9. Replace the SolidJS dependencies, lint configuration, tests, source files,
   and lockfile with the equivalents for the selected framework. The generic
   Gradle task names can remain unchanged.
10. Update the README files, test names, project metadata, and other references to
   SolidJS.

The Ktor SPA route should not need to change as long as the result is a static
client application with an `index.html`. Frameworks that require a persistent
Node.js server or server-side rendering are outside this simple swap and require
a different deployment design.

After the replacement, verify the complete integration:

```shell
./gradlew clean test
./gradlew run
```

Open <http://localhost:8080>, visit a client-side user route directly, and check
that both the page and its Ktor API request succeed.
