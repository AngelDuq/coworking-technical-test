# Testing de Endpoints - Sedes

Base URL: `http://localhost:8080`

---

## 1. Obtener Token (Login)

Todos los endpoints de sedes requieren autenticación con rol **ADMIN**. Primero obtén el token:

**POST** `/auth/login`

```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "admin@mail.com",
  "password": "admin"
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "email": "admin@mail.com",
  "rol": "ROLE_ADMIN"
}
```

> Copia el valor del campo `token` y úsalo en el header `Authorization: Bearer <token>` en todas las siguientes peticiones.

---

## 2. Crear Sede

**POST** `/api/sedes`

```http
POST http://localhost:8080/api/sedes
Content-Type: application/json
Authorization: Bearer <token>

{
  "nombre": "Sede Centro",
  "direccion": "Calle 10 # 5-30, Bogotá",
  "capacidadMaxSimultanea": 20,
  "precioHora": 8000.00
}
```

**Respuesta exitosa (201 Created):**
```json
{
  "id": 1,
  "nombre": "Sede Centro",
  "direccion": "Calle 10 # 5-30, Bogotá",
  "capacidadMaxSimultanea": 20,
  "precioHora": 8000.00,
  "operadorId": null,
  "operadorNombre": null
}
```

**Errores posibles:**

| Caso | Status | Descripción |
|------|--------|-------------|
| Sin token | `401 Unauthorized` | Token ausente o inválido |
| Token de OPERADOR | `403 Forbidden` | Solo ADMIN puede acceder |
| Campo vacío | `400 Bad Request` | Validación fallida |

**Ejemplo - Validación fallida (400):**
```json
{
  "status": 400,
  "error": "Validation Error",
  "errors": {
    "nombre": "El nombre es obligatorio",
    "precioHora": "El precio por hora debe ser mayor a 0"
  }
}
```

---

## 3. Obtener Todas las Sedes

**GET** `/api/sedes`

```http
GET http://localhost:8080/api/sedes
Authorization: Bearer <token>
```

**Respuesta exitosa (200 OK):**
```json
[
  {
    "id": 1,
    "nombre": "Sede Centro",
    "direccion": "Calle 10 # 5-30, Bogotá",
    "capacidadMaxSimultanea": 20,
    "precioHora": 8000.00,
    "operadorId": null,
    "operadorNombre": null
  },
  {
    "id": 2,
    "nombre": "Sede Norte",
    "direccion": "Av. 15 # 120-45, Bogotá",
    "capacidadMaxSimultanea": 15,
    "precioHora": 10000.00,
    "operadorId": 2,
    "operadorNombre": "Carlos González"
  }
]
```

---

## 4. Obtener Sede por ID

**GET** `/api/sedes/{id}`

```http
GET http://localhost:8080/api/sedes/1
Authorization: Bearer <token>
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Sede Centro",
  "direccion": "Calle 10 # 5-30, Bogotá",
  "capacidadMaxSimultanea": 20,
  "precioHora": 8000.00,
  "operadorId": null,
  "operadorNombre": null
}
```

**Sede no encontrada (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "No se encontro la sede.",
  "path": "/api/sedes/99",
  "timestamp": "2026-03-04T10:00:00"
}
```

---

## 5. Actualizar Sede

**PUT** `/api/sedes/{id}`

```http
PUT http://localhost:8080/api/sedes/1
Content-Type: application/json
Authorization: Bearer <token>

{
  "nombre": "Sede Centro Actualizada",
  "direccion": "Calle 12 # 8-50, Bogotá",
  "capacidadMaxSimultanea": 25,
  "precioHora": 9000.00
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Sede Centro Actualizada",
  "direccion": "Calle 12 # 8-50, Bogotá",
  "capacidadMaxSimultanea": 25,
  "precioHora": 9000.00,
  "operadorId": null,
  "operadorNombre": null
}
```

---

## 6. Eliminar Sede

**DELETE** `/api/sedes/{id}`

```http
DELETE http://localhost:8080/api/sedes/1
Authorization: Bearer <token>
```

**Respuesta exitosa (204 No Content):** *(sin cuerpo)*

**Sede no encontrada (404):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "No se encontro la sede.",
  "path": "/api/sedes/99",
  "timestamp": "2026-03-04T10:00:00"
}
```

---

## 7. Asignar Operador a Sede

> El usuario a asignar debe existir y tener rol **OPERADOR**. Crea primero un usuario con ese rol antes de probar este endpoint.

**PUT** `/api/sedes/{id}/operador`

```http
PUT http://localhost:8080/api/sedes/1/operador
Content-Type: application/json
Authorization: Bearer <token>

{
  "operadorId": 2
}
```

**Respuesta exitosa (200 OK):**
```json
{
  "id": 1,
  "nombre": "Sede Centro",
  "direccion": "Calle 10 # 5-30, Bogotá",
  "capacidadMaxSimultanea": 20,
  "precioHora": 8000.00,
  "operadorId": 2,
  "operadorNombre": "Carlos González"
}
```

**Errores posibles:**

| Caso | Status | Mensaje |
|------|--------|---------|
| Sede no existe | `404` | No se encontro la sede. |
| Usuario no existe | `404` | No se encontro el usuario. |
| Usuario no es OPERADOR | `400` | El usuario seleccionado no tiene el rol OPERADOR. |
| `operadorId` nulo | `400` | El ID del operador es obligatorio |

---

## Resumen de Endpoints

| Método | Endpoint | Descripción | Rol requerido |
|--------|----------|-------------|---------------|
| `POST` | `/auth/login` | Obtener token | Público |
| `POST` | `/api/sedes` | Crear sede | ADMIN |
| `GET` | `/api/sedes` | Listar sedes | ADMIN |
| `GET` | `/api/sedes/{id}` | Obtener sede | ADMIN |
| `PUT` | `/api/sedes/{id}` | Actualizar sede | ADMIN |
| `DELETE` | `/api/sedes/{id}` | Eliminar sede | ADMIN |
| `PUT` | `/api/sedes/{id}/operador` | Asignar operador | ADMIN |
