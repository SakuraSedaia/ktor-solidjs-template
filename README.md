# Ktor + SolidJS Template

This project was initially created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

* [Ktor Documentation](https://ktor.io/docs/home.html)
* [Ktor GitHub page](https://github.com/ktorio/ktor)
* [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). [Request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up).
* [SolidJS V2 Documentation](https://v2.solidjs.com/)

Want to use this template? Read the [Usage Instructions](USAGE_INSTRUCTIONS.md)

## Features

Here's a list of features included in this project:

| Name                                                                                  | Description                                                                        |
|---------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Static Content](https://start.ktor.io/p/io.ktor/server-static-content)               | Serves static files from defined locations                                         |
| [Status Pages](https://start.ktor.io/p/io.ktor/server-status-pages)                   | Provides exception handling for routes                                             |
| [KHealth](https://start.ktor.io/p/dev.hayden/server-khealth)                          | A simple and customizable health plugin                                            |
| [Content Negotiation](https://start.ktor.io/p/io.ktor/server-content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/io.ktor/server-kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |

## Building & Running

The project requires JDK 21, Node and pnpm are installed as Gradle Dependencies.

Use the Gradle wrapper from the project root:

| Task                     | Description                                                         |
|--------------------------|---------------------------------------------------------------------|
| `./gradlew test`         | Run the Kotlin tests and all SolidJS verification checks            |
| `./gradlew build`        | Build the SolidJS frontend and Ktor application                     |
| `./gradlew run`          | Build the frontend, copy it into the server resources, and run Ktor |
| `./gradlew pnpmInstall`  | Install the frontend dependencies from the lockfile                 |
| `./gradlew buildFrontend` | Build the frontend                                                 |

Frontend checks are run from `src/main/solidjs`:

| Command                  | Description                     |
|--------------------------|---------------------------------|
| `pnpm test --run`        | Run the SolidJS tests once      |
| `pnpm lint`              | Check the frontend with Oxlint  |
| `pnpm exec tsc --noEmit` | Check the TypeScript types      |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

The application is available at <http://localhost:8080> after the server starts.

### Development

For live frontend development, start the backend and the Vite development server in separate terminals.

From the project root, run the Ktor server:

```shell
./gradlew run
```

Then start the SolidJS development server:

```shell
cd src/main/solidjs
pnpm dev
```

Open <http://localhost:3000>. Vite proxies requests under `/api` to the Ktor server at <http://localhost:8080>.

You can create IntelliJ IDEA run configurations for both commands to start them more easily during development.

## Customize the Project Group

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

## Project Layout

- `src/main/kotlin` contains the Ktor application, plugins, and API routes.
- `src/main/resources` contains the Ktor configuration and logging configuration.
- `src/main/solidjs` contains the SolidJS frontend.
- `src/test/kotlin` contains the Ktor integration tests.

The production frontend build is written to `src/main/solidjs/dist/client`. Gradle copies that client build into the Ktor resources so the backend can serve the application and API from the same origin.

## License

This project, including both the Ktor backend and SolidJS frontend, is available under the [MIT License](LICENSE).
