# Documentación de Endpoints - Coworking API

**Base URL:** `http://localhost:8080`

---

## Índice

1. [Autenticación](#1-autenticación)
2. [Sedes (ADMIN)](#2-sedes)
3. [Usuarios (ADMIN)](#3-usuarios)
4. [Personas (ADMIN / OPERADOR)](#4-personas)
5. [Ingresos (OPERADOR)](#5-ingresos)
6. [Salidas (OPERADOR)](#6-salidas)
7. [Indicadores](#7-indicadores)
8. [Notificaciones (ADMIN / OPERADOR)](#8-notificaciones)

---

## Headers requeridos

Todos los endpoints (excepto login) requieren el header de autorización:

```
Authorization: Bearer <token_jwt>
Content-Type: application/json
```

---

## 1. Autenticación

### POST `/auth/login` — Iniciar sesión

> **Acceso:** Público (no requiere token)

**Request Body:**
```json
{
  "email": "admin@mail.com",
  "password": "admin"
}
```

**Response 200:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "rol": "ROLE_ADMIN"
}
```

**Credenciales de prueba (precargadas):**

| Rol   | Email            | Password |
|-------|------------------|----------|
| ADMIN | admin@mail.com   | admin    |

> Para obtener un token OPERADOR, primero crea un operador con el endpoint de usuarios.

---

## 2. Sedes

> **Acceso:** Todos los endpoints de sedes requieren rol **ADMIN**

### POST `/api/sedes` — Crear sede

**Request Body:**
```json
{
  "nombre": "Sede Norte",
  "direccion": "Calle 100 #15-20",
  "capacidadMaxSimultanea": 50,
  "precioHora": 15000.00
}
```

**Response 201:**
```json
{
  "id": 1,
  "nombre": "Sede Norte",
  "direccion": "Calle 100 #15-20",
  "capacidadMaxSimultanea": 50,
  "precioHora": 15000.00,
  "operadorId": null,
  "operadorNombre": null
}
```

---

### GET `/api/sedes` — Obtener todas las sedes

**Response 200:**
```json
[
  {
    "id": 1,
    "nombre": "Sede Norte",
    "direccion": "Calle 100 #15-20",
    "capacidadMaxSimultanea": 50,
    "precioHora": 15000.00,
    "operadorId": null,
    "operadorNombre": null
  }
]
```

---

### GET `/api/sedes/{id}` — Obtener sede por ID

**Ejemplo:** `GET /api/sedes/1`

**Response 200:**
```json
{
  "id": 1,
  "nombre": "Sede Norte",
  "direccion": "Calle 100 #15-20",
  "capacidadMaxSimultanea": 50,
  "precioHora": 15000.00,
  "operadorId": null,
  "operadorNombre": null
}
```

---

### PUT `/api/sedes/{id}` — Actualizar sede

**Ejemplo:** `PUT /api/sedes/1`

**Request Body:**
```json
{
  "nombre": "Sede Norte Actualizada",
  "direccion": "Calle 100 #15-25",
  "capacidadMaxSimultanea": 60,
  "precioHora": 18000.00
}
```

**Response 200:**
```json
{
  "id": 1,
  "nombre": "Sede Norte Actualizada",
  "direccion": "Calle 100 #15-25",
  "capacidadMaxSimultanea": 60,
  "precioHora": 18000.00,
  "operadorId": null,
  "operadorNombre": null
}
```

---

### DELETE `/api/sedes/{id}` — Eliminar sede

**Ejemplo:** `DELETE /api/sedes/1`

**Response 204:** Sin contenido

---

### PUT `/api/sedes/{id}/operador` — Asignar operador a sede

**Ejemplo:** `PUT /api/sedes/1/operador`

**Request Body:**
```json
{
  "operadorId": 2
}
```

**Response 200:**
```json
{
  "id": 1,
  "nombre": "Sede Norte",
  "direccion": "Calle 100 #15-20",
  "capacidadMaxSimultanea": 50,
  "precioHora": 15000.00,
  "operadorId": 2,
  "operadorNombre": "Juan Pérez"
}
```

---

## 3. Usuarios

> **Acceso:** Todos los endpoints de usuarios requieren rol **ADMIN**

### POST `/api/usuarios/operadores` — Crear operador

**Request Body:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "documento": "123456789",
  "email": "juan.perez@mail.com",
  "password": "operador123"
}
```

**Response 201:**
```json
{
  "id": 2,
  "nombre": "Juan",
  "apellido": "Pérez",
  "documento": "123456789",
  "email": "juan.perez@mail.com",
  "rol": "OPERADOR"
}
```

> Después de crear el operador, puedes hacer login con sus credenciales para obtener un token con rol OPERADOR.

---

### GET `/api/usuarios` — Obtener todos los usuarios

**Response 200:**
```json
[
  {
    "id": 1,
    "nombre": "Admin",
    "apellido": "Sistema",
    "documento": "000000001",
    "email": "admin@mail.com",
    "rol": "ADMIN"
  },
  {
    "id": 2,
    "nombre": "Juan",
    "apellido": "Pérez",
    "documento": "123456789",
    "email": "juan.perez@mail.com",
    "rol": "OPERADOR"
  }
]
```

---

### GET `/api/usuarios/{id}` — Obtener usuario por ID

**Ejemplo:** `GET /api/usuarios/2`

**Response 200:**
```json
{
  "id": 2,
  "nombre": "Juan",
  "apellido": "Pérez",
  "documento": "123456789",
  "email": "juan.perez@mail.com",
  "rol": "OPERADOR"
}
```

---

### PUT `/api/usuarios/{id}` — Actualizar usuario

**Ejemplo:** `PUT /api/usuarios/2`

**Request Body:**
```json
{
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "documento": "123456789",
  "email": "juanc.perez@mail.com"
}
```

**Response 200:**
```json
{
  "id": 2,
  "nombre": "Juan Carlos",
  "apellido": "Pérez López",
  "documento": "123456789",
  "email": "juanc.perez@mail.com",
  "rol": "OPERADOR"
}
```

**Posibles errores:**
- `404` — No se encontró el usuario
- `400` — Ya existe un usuario registrado con ese email o documento

---

### DELETE `/api/usuarios/{id}` — Eliminar usuario

**Ejemplo:** `DELETE /api/usuarios/2`

**Response 204:** Sin contenido

---

## 4. Personas

> **Acceso:** Todos los endpoints de personas requieren rol **ADMIN** o **OPERADOR**

### POST `/api/personas` — Crear persona

**Request Body:**
```json
{
  "documento": "1001234567",
  "nombre": "María",
  "apellido": "García",
  "email": "maria.garcia@mail.com"
}
```

**Response 201:**
```json
{
  "id": 1,
  "documento": "1001234567",
  "nombre": "María",
  "apellido": "García",
  "email": "maria.garcia@mail.com"
}
```

**Posibles errores:**
- `400` — Ya existe una persona registrada con ese documento

---

### GET `/api/personas` — Obtener todas las personas

**Response 200:**
```json
[
  {
    "id": 1,
    "documento": "1001234567",
    "nombre": "María",
    "apellido": "García",
    "email": "maria.garcia@mail.com"
  }
]
```

---

### GET `/api/personas/{id}` — Obtener persona por ID

**Ejemplo:** `GET /api/personas/1`

**Response 200:**
```json
{
  "id": 1,
  "documento": "1001234567",
  "nombre": "María",
  "apellido": "García",
  "email": "maria.garcia@mail.com"
}
```

---

### PUT `/api/personas/{id}` — Actualizar persona

**Ejemplo:** `PUT /api/personas/1`

**Request Body:**
```json
{
  "documento": "1001234567",
  "nombre": "María Fernanda",
  "apellido": "García López",
  "email": "mariaf.garcia@mail.com"
}
```

**Response 200:**
```json
{
  "id": 1,
  "documento": "1001234567",
  "nombre": "María Fernanda",
  "apellido": "García López",
  "email": "mariaf.garcia@mail.com"
}
```

**Posibles errores:**
- `404` — No se encontró la persona
- `400` — Ya existe una persona registrada con ese documento

---

### DELETE `/api/personas/{id}` — Eliminar persona

**Ejemplo:** `DELETE /api/personas/1`

**Response 204:** Sin contenido

---

## 5. Ingresos

> **Acceso:** Requiere rol **OPERADOR**

### POST `/api/ingresos` — Registrar ingreso de persona

**Request Body:**
```json
{
  "documento": "1001234567",
  "nombre": "María",
  "apellido": "García",
  "sedeId": 1
}
```

**Response 201:**
```json
{
  "id": 1,
  "documento": "1001234567",
  "nombreCompleto": "María García",
  "sedeId": 1,
  "sedeNombre": "Sede Norte",
  "fechaHoraIngreso": "2026-03-04T10:30:00"
}
```

**Posibles errores:**
- `400` — La persona ya tiene un ingreso activo en una sede
- `400` — La sede ha alcanzado su capacidad máxima simultánea
- `404` — No se encontró la sede

---

## 6. Salidas

> **Acceso:** Requiere rol **OPERADOR**

### POST `/api/salidas` — Registrar salida de persona

**Request Body:**
```json
{
  "documento": "1001234567"
}
```

**Response 200:**
```json
{
  "id": 1,
  "documento": "1001234567",
  "nombreCompleto": "María García",
  "sedeId": 1,
  "sedeNombre": "Sede Norte",
  "fechaHoraIngreso": "2026-03-04T10:30:00",
  "fechaHoraSalida": "2026-03-04T13:45:00",
  "valorPagar": 60000.00
}
```

**Posibles errores:**
- `404` — No se encontró la persona
- `404` — No se encontró un ingreso activo para esta persona

> Al registrar la salida: se elimina el ingreso, se crea el registro en histórico, se calcula el valor a pagar y se verifica si aplica cupón de fidelidad (+20 horas acumuladas en la sede).

---

## 7. Indicadores

### GET `/api/indicadores/top-personas` — Top 10 personas con más ingresos

> **Acceso:** **ADMIN** o **OPERADOR**

**Response 200:**
```json
[
  {
    "documento": "1001234567",
    "nombreCompleto": "María García",
    "totalIngresos": 15
  }
]
```

---

### GET `/api/indicadores/top-personas/sede/{sedeId}` — Top 10 personas con más ingresos por sede

> **Acceso:** **ADMIN** o **OPERADOR**

**Ejemplo:** `GET /api/indicadores/top-personas/sede/1`

**Response 200:**
```json
[
  {
    "documento": "1001234567",
    "nombreCompleto": "María García",
    "totalIngresos": 8
  }
]
```

---

### GET `/api/indicadores/primer-ingreso` — Personas que ingresan por primera vez

> **Acceso:** **ADMIN** o **OPERADOR**

**Response 200:**
```json
[
  {
    "documento": "2009876543",
    "nombreCompleto": "Carlos López",
    "sedeNombre": "Sede Norte",
    "fechaHoraIngreso": "2026-03-04T09:00:00"
  }
]
```

---

### GET `/api/indicadores/ingresos-economicos` — Ingresos económicos de la sede del operador

> **Acceso:** Solo **OPERADOR** (usa el token del operador autenticado para determinar su sede)

**Response 200:**
```json
{
  "ingresoHoy": 150000.00,
  "ingresoSemana": 850000.00,
  "ingresoMes": 3200000.00,
  "ingresoAnio": 38500000.00
}
```

---

### GET `/api/indicadores/top-operadores` — Top 3 operadores con más ingresos en la semana

> **Acceso:** Solo **ADMIN**

**Response 200:**
```json
[
  {
    "operadorId": 2,
    "nombreCompleto": "Juan Pérez",
    "totalIngresos": 45
  }
]
```

---

### GET `/api/indicadores/top-sedes-facturacion` — Top 3 sedes con mayor facturación semanal

> **Acceso:** Solo **ADMIN**

**Response 200:**
```json
[
  {
    "sedeId": 1,
    "sedeNombre": "Sede Norte",
    "facturacionSemanal": 2500000.00
  }
]
```

---

## 8. Notificaciones

> **Acceso:** **ADMIN** o **OPERADOR**

### POST `/api/notificaciones` — Enviar notificación simulada

**Request Body:**
```json
{
  "email": "maria.garcia@mail.com",
  "documento": "1001234567",
  "mensaje": "Gracias por tu fidelidad! Has recibido un cupón de consumo interno.",
  "sedeId": 1,
  "sedeNombre": "Sede Norte"
}
```

**Response 200:**
```json
{
  "exitoso": true,
  "mensaje": "Notificacion enviada exitosamente a maria.garcia@mail.com"
}
```

> Este endpoint imprime la notificación en los logs del servidor (microservicio simulado). También se invoca automáticamente cuando se genera un cupón de fidelidad al registrar una salida.

---

## Flujo de prueba sugerido

1. **Login como ADMIN** → `POST /auth/login` con `admin@mail.com` / `admin`
2. **Crear sede** → `POST /api/sedes`
3. **Crear operador** → `POST /api/usuarios/operadores`
4. **Asignar operador a sede** → `PUT /api/sedes/{id}/operador`
5. **Login como OPERADOR** → `POST /auth/login` con las credenciales del operador creado
6. **Registrar ingreso** → `POST /api/ingresos`
7. **Registrar salida** → `POST /api/salidas`
8. **Consultar indicadores** → `GET /api/indicadores/...`
9. **Enviar notificación** → `POST /api/notificaciones`
10. **Consultar indicadores ADMIN** → Login como ADMIN y `GET /api/indicadores/top-operadores` y `/top-sedes-facturacion`
