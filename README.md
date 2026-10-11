# Sport Club Management

A Spring Boot web application for presenting a sports club and managing its members. The project combines server-rendered pages with a REST API, persistence through Spring Data JPA, API documentation, automated tests, and deployment/monitoring configuration files.

> **Project status:** The source code and configuration files are present in the repository, but the Docker, Kubernetes, CI/CD, and monitoring setups should be validated before being used in a production environment. See [Configuration notes](#configuration-notes).

## Overview

The application provides a small public-facing website and a member-management API.

### Web pages

The Spring MVC controllers expose these pages:

- `/` — Home
- `/members` — Members page
- `/about` — About the club
- `/activities` — Sports activities
- `/contact` — Contact page

The pages are rendered with Thymeleaf templates in `src/main/resources/templates`.

### Member management API

The REST API is rooted at `/api/members`.

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/api/members` | List all members |
| `GET` | `/api/members/{id}` | Retrieve a member by ID |
| `POST` | `/api/members` | Create a member |
| `PUT` | `/api/members/{id}` | Update a member |
| `DELETE` | `/api/members/{id}` | Delete a member |
| `GET` | `/api/members/search?name={lastName}` | Search members by last name, ignoring case |
| `GET` | `/api/members/count` | Return the total number of members |

The API uses JSON request and response bodies for member operations. The controller documents its operations with OpenAPI annotations.

### Member data

The JPA `Member` entity contains:

- `id` — generated identifier
- `firstName` and `lastName` — required name fields
- `email` — unique when provided
- `phone`
- `birthDate`
- `address`
- `registrationDate` — defaults to the current date
- `status` — defaults to `ACTIVE`; the model comment also mentions `INACTIVE` and `PENDING`

The service checks for an existing email when creating a member and checks for existence before deletion. The current implementation does not yet provide a dedicated, consistent API error contract for all invalid or missing-resource cases.

## Technology stack

- **Java 17**
- **Spring Boot 3.2.3**
- Spring MVC / Spring Web
- Spring Data JPA
- Thymeleaf
- Bean Validation dependency
- H2 database for the default local configuration and tests
- Lombok
- Springdoc OpenAPI / Swagger UI
- Spring Boot Actuator
- Micrometer Prometheus registry
- Maven
- Docker and Docker Compose configuration
- Kubernetes manifests
- GitHub Actions workflow files
- JaCoCo, SonarCloud and Cucumber test dependencies

## Architecture

The application follows a layered Spring Boot structure:

```text
src/main/java/com/sportclub/
├── SportClubApplication.java
├── controller/
│   ├── MainController.java
│   ├── MemberController.java
│   └── GlobalController.java
├── model/
│   └── Member.java
├── repository/
│   └── MemberRepository.java
└── service/
    └── MemberService.java

src/main/resources/
├── application.properties
├── application-docker.properties
├── application-monitoring.properties
└── templates/
    ├── layout.html
    ├── index.html
    ├── members.html
    ├── about.html
    ├── activities.html
    └── contact.html
```

- **Controllers** handle page navigation and HTTP requests.
- **Service** contains member-management operations.
- **Repository** uses Spring Data JPA to access member records.
- **Model** defines the persisted member entity.
- **Thymeleaf templates** render the web pages.

## Prerequisites

For local development, install:

- JDK 17
- Git
- Maven, or use the included Maven Wrapper

Docker is only needed if you want to work with the provided container configuration.

## Run locally

The default `application.properties` configuration uses an **in-memory H2 database**. Data in this database is not intended to persist after the application stops.

1. Clone the repository:

   ```bash
   git clone https://github.com/WissemAttouf/sport-club-management.git
   cd sport-club-management
   ```

2. Start the application with the Maven Wrapper:

   **Linux/macOS**
   ```bash
   ./mvnw spring-boot:run
   ```

   **Windows**
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

3. Open the site at [http://localhost:8083](http://localhost:8083).

The default application port is set to `8083` in `src/main/resources/application.properties`.

## API documentation

The repository configures Springdoc with these paths:

- Swagger UI: [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8083/api-docs](http://localhost:8083/api-docs)

These links are available when the application starts successfully with the current configuration.

## Run tests

Run the automated test suite with:

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

Tests are located under `src/test/java`. The repository includes Spring Boot application, controller, service, and repository tests, and uses an H2 in-memory database through `src/test/resources/application-test.properties`.

A test runner script, `run-tests.sh`, and a Docker Compose test configuration are also present. Their full integration-test workflow should be checked before relying on them in CI.

## Docker, Kubernetes and monitoring

The repository includes configuration files for the following workflows:

- **Docker:** `Dockerfile`, `docker-compose.yml`
- **Docker test environment:** `docker-compose.test.yml`
- **Monitoring:** `docker-compose.monitoring.yml`, `prometheus/prometheus.yml`, `grafana/datasources.yml`
- **Kubernetes:** manifests under `k8s/`
- **CI:** `.github/workflows/ci.yml`
- **Tag-triggered Kubernetes deployment workflow:** `.github/workflows/deploy.yml`
- **Code coverage and analysis:** JaCoCo and SonarCloud configuration

These files document the intended tooling, but their presence does not guarantee that the complete deployment pipeline currently works end to end.

### Configuration notes

Before using the container or deployment configurations, review these repository-specific details:

1. **Port mismatch:** the default application configuration sets port `8083`, while the Dockerfile and Compose port mapping use `8080`. Align the application and container ports before running the container.
2. **Database driver:** the Docker profile references MySQL and `com.mysql.cj.jdbc.Driver`, but the current `pom.xml` lists H2 and does not declare the MySQL Connector/J dependency. Add and verify the driver before using the MySQL profile.
3. **Credentials:** database and monitoring credentials are currently specified directly in configuration files. Replace them with environment variables or a secrets manager; do not use the repository's sample values in a real environment.
4. **Kubernetes and CI/CD:** confirm that the selected Spring profile, image name/registry, database connection, service ports, and required GitHub Actions secrets match the target cluster.
5. **Monitoring:** confirm that the application, Prometheus and Grafana share the expected network and that the Prometheus scrape target is reachable.
6. **Production hardening:** review database schema management, H2 console exposure, Actuator endpoint access, input validation, and access control before exposing the application publicly.

## Repository layout

```text
.
├── .github/workflows/       # GitHub Actions workflows
├── docker/                  # Docker helper scripts
├── grafana/                 # Grafana datasource configuration
├── k8s/                     # Kubernetes manifests and helper script
├── prometheus/              # Prometheus configuration
├── src/main/java/           # Application source code
├── src/main/resources/      # Properties and Thymeleaf templates
├── src/test/                # Automated tests and test properties
├── Dockerfile
├── docker-compose.yml
├── docker-compose.test.yml
├── docker-compose.monitoring.yml
├── pom.xml
├── mvnw
└── run-tests.sh
```

## Contributing

Contributions and issue reports are welcome. When proposing a change, please include a clear description of the problem, the expected behavior, and the tests performed.

## License

No license file is currently present in the repository. Unless a license is added, reuse and redistribution permissions should not be assumed.
