# Libros API — Toscano Post-1 U12

API REST de gestión de libros desarrollada con Spring Boot 3.2, 
contenedorizada con Docker y desplegada en Railway.

![CI/CD Status](https://github.com/AndressToscanom30/toscano-post1-u12/actions/workflows/ci.yml/badge.svg)

## Pipeline CI/CD

El pipeline se activa automáticamente en cada push a `main` y realiza:

1. Compilación con Maven y ejecución de pruebas unitarias
2. Generación de reporte de cobertura JaCoCo (artefacto descargable en cada ejecución)
3. Construcción de imagen Docker con multi-stage build
4. Publicación en Docker Hub con tags `latest` y `sha-<commit>`

## Tecnologías
- Java 21 / Spring Boot 3.2
- Spring Data JPA + PostgreSQL (producción) / H2 (desarrollo)
- Docker multi-stage build
- Railway (despliegue en la nube)

## Endpoints disponibles

| Método | Ruta                        | Descripción                  |
|--------|-----------------------------|------------------------------|
| GET    | /api/libros                 | Listar todos los libros       |
| GET    | /api/libros/{id}            | Obtener libro por ID          |
| GET    | /api/libros/disponibles     | Listar libros disponibles     |
| GET    | /api/libros/genero/{genero} | Filtrar por género            |
| GET    | /api/libros/autor?nombre=X  | Buscar por autor              |
| POST   | /api/libros                 | Crear libro                   |
| PUT    | /api/libros/{id}            | Actualizar libro              |
| DELETE | /api/libros/{id}            | Eliminar libro                |
| GET    | /actuator/health            | Estado de salud de la app     |

## Ejecutar localmente (sin Docker)

```bash
mvn spring-boot:run
# Acceder en http://localhost:8080
```

## Construir imagen Docker

```bash
docker build -t libros-app:local .
docker images   # verificar tamaño < 300 MB
```

## Ejecutar con Docker Compose (app + PostgreSQL)

```bash
docker compose up -d --build
docker compose ps                         # verificar estado
curl http://localhost:8080/actuator/health
curl http://localhost:8080/api/libros
```

## Variables de entorno requeridas (producción)

| Variable               | Descripción                        |
|------------------------|------------------------------------|
| `DATABASE_URL`         | URL JDBC de PostgreSQL              |
| `DB_USER`              | Usuario de la base de datos         |
| `DB_PASS`              | Contraseña de la base de datos      |
| `SPRING_PROFILES_ACTIVE` | Debe ser `prod`                  |

## Despliegue en Railway

1. Crear proyecto en [railway.app](https://railway.app) → "Deploy from GitHub repo"
2. Agregar servicio PostgreSQL: `+ New → Database → Add PostgreSQL`
3. En el servicio de la app, ir a **Variables** y configurar:
   - `SPRING_PROFILES_ACTIVE` = `prod`
   - `DATABASE_URL` = `${{Postgres.DATABASE_URL}}`
   - `DB_USER` = `${{Postgres.PGUSER}}`
   - `DB_PASS` = `${{Postgres.PGPASSWORD}}`
4. En **Settings → Networking → Generate Domain** obtener la URL pública.

## URL de la aplicación en Railway

> https://toscano-post1-u12.up.railway.app  ← (reemplazar con tu URL real)

## Verificación del despliegue

```bash
curl https://toscano-post1-u12-production.up.railway.app/actuator/health
curl https://toscano-post1-u12-production.up.railway.app/api/libros
```