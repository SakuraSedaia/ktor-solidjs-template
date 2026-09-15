# ktor-solidjs-template

This project was initially created using the [Ktor Project Generator](https://start.ktor.io).

Here are some useful links to get you started:

* [Ktor Documentation](https://ktor.io/docs/home.html)
* [Ktor GitHub page](https://github.com/ktorio/ktor)
* [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). [Request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up).
* [SolidJS V2 Documentation](https://v2.solidjs.com/)

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

The project requires JDK 21, Node.js 22.12 or newer, and pnpm 10.33.2 or newer within the 10.x release line.

Use the Gradle wrapper from the project root:

| Task                     | Description                                                          |
|--------------------------|----------------------------------------------------------------------|
| `./gradlew test`         | Run the Kotlin tests                                                  |
| `./gradlew build`        | Build the SolidJS frontend and Ktor application                      |
| `./gradlew run`          | Build the frontend, copy it into the server resources, and run Ktor  |
| `./gradlew pnpmInstall`  | Install the frontend dependencies from the lockfile                  |
| `./gradlew buildSolidJs` | Build the SolidJS frontend                                            |

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
