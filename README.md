# Street Art Explorer – Auth Server

Authorization and Identity service for **Street Art Explorer**.  
Implements **Spring Authorization Server** (OAuth2 / OIDC) to issue JWTs for the client and the resource server, with optional **social login** via Google and GitHub.

---

## Main Features

- **OAuth2 / OpenID Connect** Authorization Server (JWT issuance).
- Standard endpoints (authorization, token, JWKs, OIDC discovery).
- **Social login** (Google & GitHub) via Spring Security OAuth2 Client.
- **User & Client registration** helper endpoints.
- **PostgreSQL + Flyway** for users/clients persistence.
- **CORS** configured for `http://localhost:4200` (adjust for prod).
- Built with **Spring Boot 3** and **Java 21**.

---

## Tech Stack

- **Java 21**, **Maven**
- **Spring Boot 3.3.x**
  - `spring-boot-starter-oauth2-authorization-server`
  - `spring-boot-starter-security`
  - `spring-boot-starter-web` (+ `webflux` where needed)
  - `spring-boot-starter-data-jpa`
- **PostgreSQL**
- **Flyway** (`src/main/resources/db/migration`)
- **Lombok**, **Jakarta Validation**


---

## Security & Endpoints

This service runs **Spring Authorization Server**. Common endpoints:

- **Authorization**: `/oauth2/authorize`
- **Token**: `/oauth2/token`
- **JWK Set**: `/oauth2/jwks`
- **OIDC Discovery**: `/.well-known/openid-configuration`
- **Login**: `/login` (form + social providers)

Public helper controllers (adapt/remove for production):

- `POST /client/register` — register OAuth clients in DB.
- `POST /user/register` — register local users (if using password grant scenarios or admin flows).

**CORS** allows `http://localhost:4200` by default (see `SecurityConfig`). Adjust in production.

---

## Social Login

Configured providers (via properties):

- **Google** (OIDC)
- **GitHub** (OAuth2)

Set your own **client-id** and **client-secret** via environment variables (see configuration below).  
Redirect URIs (examples):
- `http://localhost:9000/login/oauth2/code/google`
- `http://localhost:9000/login/oauth2/code/github`

---

## Database & Migrations

- **PostgreSQL** with **Flyway** migrations in `src/main/resources/db/migration`:
  - `V1__create_users_schema.sql`
  - `V2__create_clients_schema.sql`
  - `V3__add_redirect_uri_and_auth_method_for_client.sql`

> These scripts create tables for users/roles and OAuth clients with allowed grants, redirect URIs and auth methods.

---

## Configuration

Prefer **environment variables** (or a local-only `application-dev.properties`).  
Typical settings:

```properties
# Server
server.port=9000

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/sae-auth-server
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}

# JPA / Flyway
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

# Logging (optional while developing)
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.security.oauth2=DEBUG

# OAuth2 Client - Google
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.scope=openid, email, profile
spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:9000/login/oauth2/code/google

# OAuth2 Client - GitHub
spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
spring.security.oauth2.client.registration.github.scope=user:email
spring.security.oauth2.client.registration.github.redirect-uri=http://localhost:9000/login/oauth2/code/github

# Resource Server URL (for back-channel sync, if used)
resource.server.url=${RESOURCE_SERVER_URL:http://localhost:9090}

# Internal token for service-to-service calls (set your own secret)
internal.auth.token=${INTERNAL_AUTH_TOKEN}
```

Recommended env vars for local dev:

```properties
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=postgres
export GOOGLE_CLIENT_ID=your-google-client-id
export GOOGLE_CLIENT_SECRET=your-google-client-secret
export GITHUB_CLIENT_ID=your-github-client-id
export GITHUB_CLIENT_SECRET=your-github-client-secret
export RESOURCE_SERVER_URL=http://localhost:9090
export INTERNAL_AUTH_TOKEN=change-me
```

---

## Running Locally

See resource-server documentation.
