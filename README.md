# Sistema de Gestión de Préstamos Bancarios — Backend

API REST en Spring Boot: solicitar préstamos, aprobarlos o rechazarlos, y consultar su estado.

## Cómo correrlo

Requiere JDK 17.

```
./mvnw spring-boot:run
```

Levanta en `localhost:8080` y siembra dos usuarios:

| Correo | Contraseña | Rol |
|---|---|---|
| usuario@test.com | 123 | USUARIO |
| admin@test.com | 123 | ADMINISTRADOR |

Swagger: `localhost:8080/swagger-ui.html`
Consola H2: `localhost:8080/h2-console` (`jdbc:h2:mem:loansdb`, user `sa`, sin password)

Tests: `./mvnw test`

## Endpoints

| Método | Ruta | Rol | Qué hace |
|---|---|---|---|
| POST | `/api/auth/login` | público | login, devuelve el JWT |
| POST | `/api/users` | público | crear usuario nuevo |
| POST | `/api/loans` | USUARIO | pedir un préstamo |
| GET | `/api/loans/me` | USUARIO | ver mis préstamos |
| GET | `/api/loans` | ADMIN | ver todos |
| PATCH | `/api/loans/{id}/approve` | ADMIN | aprobar |
| PATCH | `/api/loans/{id}/reject` | ADMIN | rechazar |

## Arquitectura

- `domain`: modelos y reglas de negocio
- `application`: casos de uso (`port.in`, `port.out`)
- `infrastructure`: JPA, seguridad, controllers
