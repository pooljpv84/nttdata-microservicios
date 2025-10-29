# 🚀 Ejecución del Proyecto con Docker
#### Paul Velasco
### ▶️ Comando para ejecutar

Desde la raíz del proyecto, ejecuta:

```bash
docker compose up -d
```
🌐 Servicios disponibles

### Kafka: http://localhost:19000/
### Micro 1: http://localhost:8081/
### Micro 2: http://localhost:8082/

# Endpoints (micro1) — Resumen

Host base: http://localhost:8081

## Personas
- POST    http://localhost:8081/api/personas/post
- PUT     http://localhost:8081/api/personas/put/{id}
- GET     http://localhost:8081/api/personas/getPersona/{id}
- GET     http://localhost:8081/api/personas/getAll
- DELETE  http://localhost:8081/api/personas/delete/{id}

## Clientes
- POST   http://localhost:8081/api/clientes/post
- GET    http://localhost:8081/api/clientes/getAll
- GET    http://localhost:8081/api/clientes/getByClientePk/{id}
- GET    http://localhost:8081/api/clientes/getByPersonaPk/{id}
- PUT    http://localhost:8081/api/clientes/put/{id}
- DELETE http://localhost:8081/api/clientes/delete/{id}
- GET    http://localhost:8081/api/clientes/getByClienteId/{clienteId}
- GET    http://localhost:8081/api/clientes/getByIdentificacion/{identificacion}
- PATCH  http://localhost:8081/api/clientes/patchEstado/{clienteId}?activo=true|false

## Administración
- POST   http://localhost:8081/api/clientes/admin/publishAll

## Swagger
- GET    http://localhost:8081/swagger-ui/index.html
- GET    http://localhost:8081/  (redirige a Swagger UI)


# Endpoints (micro2) — Resumen

Host base: http://localhost:8082

## Cuentas
- POST   http://localhost:8082/api/cuentas/post
- GET    http://localhost:8082/api/cuentas/get/{id}
- GET    http://localhost:8082/api/cuentas/getAllCuentas
- GET    http://localhost:8082/api/cuentas/getByCliente/{clienteId}
- PATCH  http://localhost:8082/api/cuentas/patchEstado/{id}?activa=true|false

## Movimientos
- POST   http://localhost:8082/api/movimientos/postMovimiento
- GET    http://localhost:8082/api/movimientos/getByCuenta/{cuentaId}

## Reportes
- GET    http://localhost:8082/api/reportes?clienteId={id}&desde=YYYY-MM-DD&hasta=YYYY-MM-DD
- GET    http://localhost:8082/api/reportes?clienteId={id}&fecha=YYYY-MM-DD,YYYY-MM-DD
  - Nota: el parámetro `fecha` acepta un rango separado por coma. También puedes usar `desde` y `hasta` por separado.

## Swagger
- GET    http://localhost:8082/swagger  (UI de Swagger según configuración)
- GET    http://localhost:8082/v3/api-docs  (OpenAPI JSON)
- GET    http://localhost:8082/  (redirige a Swagger UI)
