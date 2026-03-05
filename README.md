# Coworking API - Technical Test

API REST para la gestión de espacios de coworking desarrollada con Spring Boot. Incluye autenticación JWT, control de acceso por roles (ADMIN/OPERADOR), gestión de sedes, registro de ingresos/salidas de personas, indicadores de negocio, sistema de cupones de fidelidad y notificaciones simuladas.

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 4.0.3 |
| Spring Security | JWT (jjwt 0.12.6) |
| Spring Data JPA | Hibernate |
| MySQL | 8.x |
| Lombok | Última estable |
| SpringDoc OpenAPI | 2.8.6 (Swagger UI) |
| Maven | Wrapper incluido |

---

## Requisitos previos

- **Java 17** o superior instalado → [Descargar JDK](https://adoptium.net/)
- **MySQL 8.x** instalado y corriendo en `localhost:3306` → [Descargar MySQL](https://dev.mysql.com/downloads/)
- **Git** (para clonar el repositorio)

> No es necesario instalar Maven, el proyecto incluye el wrapper (`mvnw` / `mvnw.cmd`).

---

## Configuración de la base de datos

1. Abre un cliente MySQL (MySQL Workbench, terminal, DBeaver, etc.)

2. Crea la base de datos:

```sql
CREATE DATABASE IF NOT EXISTS coworking;
```

3. La aplicación se conecta a MySQL mediante **variables de entorno**. El archivo `src/main/resources/application.properties` contiene:

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOSTNAME}:${MYSQL_PORT}/${MYSQL_DATABASE}?...
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
```

Debes definir las siguientes variables de entorno antes de ejecutar la aplicación:

| Variable de entorno | Descripción | Ejemplo |
|---|---|---|
| `MYSQL_HOSTNAME` | Host donde corre MySQL | `localhost` |
| `MYSQL_PORT` | Puerto de MySQL | `3306` |
| `MYSQL_DATABASE` | Nombre de la base de datos | `coworking` |
| `MYSQL_USERNAME` | Usuario de MySQL | `root` |
| `MYSQL_PASSWORD` | Contraseña de MySQL | `1234` |

### Opción A — Variables de entorno en terminal

**Windows (CMD):**
```cmd
set MYSQL_HOSTNAME=localhost
set MYSQL_PORT=3306
set MYSQL_DATABASE=coworking
set MYSQL_USERNAME=root
set MYSQL_PASSWORD=1234
.\mvnw.cmd spring-boot:run
```

**Windows (PowerShell):**
```powershell
$env:MYSQL_HOSTNAME="localhost"
$env:MYSQL_PORT="3306"
$env:MYSQL_DATABASE="coworking"
$env:MYSQL_USERNAME="root"
$env:MYSQL_PASSWORD="1234"
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**
```bash
export MYSQL_HOSTNAME=localhost
export MYSQL_PORT=3306
export MYSQL_DATABASE=coworking
export MYSQL_USERNAME=root
export MYSQL_PASSWORD=1234
./mvnw spring-boot:run
```

### Opción B — Conexión local hardcodeada (desarrollo rápido)

Si prefieres evitar las variables de entorno para desarrollo local, el archivo `application.properties` incluye las credenciales locales comentadas. Solo descomenta esas líneas y comenta las que usan variables de entorno:

```properties
# Descomenta estas líneas para conexión local directa:
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/coworking?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Bogota
spring.datasource.username=root
spring.datasource.password=1234

# Comenta o elimina estas líneas:
# spring.datasource.url=jdbc:mysql://${MYSQL_HOSTNAME}:${MYSQL_PORT}/${MYSQL_DATABASE}?...
# spring.datasource.username=${MYSQL_USERNAME}
# spring.datasource.password=${MYSQL_PASSWORD}
```

> Las credenciales locales preconfiguradas son `root` / `1234`. Ajústalas según tu entorno si es necesario.

4. Las tablas y datos iniciales se crean automáticamente al iniciar la aplicación (`ddl-auto=update` + `data.sql`).

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/AngelDuq/coworking-technical-test.git
cd coworking-technical-test
```

### 2. Compilar el proyecto

**Windows:**
```cmd
.\mvnw.cmd clean compile
```

**Linux / macOS:**
```bash
./mvnw clean compile
```

### 3. Ejecutar la aplicación

**Windows:**
```cmd
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**
```bash
./mvnw spring-boot:run
```

La aplicación se iniciará en: **http://localhost:8080**

### 4. Verificar que está corriendo

Puedes probar el endpoint de login:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@mail.com", "password": "admin"}'
```

---

## Documentación de la API

### Swagger UI

Una vez la aplicación esté corriendo, accede a la documentación interactiva en:

**http://localhost:8080/swagger-ui/index.html**

Desde Swagger UI puedes:
1. Hacer clic en **Authorize** (botón con candado)
2. Ingresar el token JWT con formato: `Bearer <tu_token>`
3. Probar todos los endpoints directamente desde el navegador

### Documentación en Markdown

La documentación detallada de todos los endpoints con ejemplos de request/response se encuentra en el archivo [api-endpoints.md](api-endpoints.md).

---

## Datos precargados

Al iniciar la aplicación, se crean automáticamente:

| Dato | Valor |
|---|---|
| Rol ADMIN | ID: 1 |
| Rol OPERADOR | ID: 2 |
| Usuario Admin | Email: `admin@mail.com` / Password: `admin` |

---

## Credenciales de acceso

| Rol | Email | Password |
|---|---|---|
| ADMIN | admin@mail.com | admin |

> Para obtener credenciales de OPERADOR, primero crea un operador desde el endpoint `POST /api/usuarios/operadores` usando el token de ADMIN.

---

## Endpoints disponibles

| # | Método | Ruta | Descripción | Acceso |
|---|---|---|---|---|
| 1 | POST | `/auth/login` | Iniciar sesión | Público |
| 2 | POST | `/auth/logout` | Cerrar sesión / invalidar token | Autenticado |
| 3 | POST | `/api/sedes` | Crear sede | ADMIN |
| 4 | GET | `/api/sedes` | Obtener todas las sedes | ADMIN |
| 5 | GET | `/api/sedes/{id}` | Obtener sede por ID | ADMIN |
| 6 | PUT | `/api/sedes/{id}` | Actualizar sede | ADMIN |
| 7 | DELETE | `/api/sedes/{id}` | Eliminar sede | ADMIN |
| 8 | PUT | `/api/sedes/{id}/operador` | Asignar operador a sede | ADMIN |
| 9 | POST | `/api/usuarios/operadores` | Crear operador | ADMIN |
| 10 | GET | `/api/usuarios` | Obtener todos los usuarios | ADMIN |
| 11 | GET | `/api/usuarios/{id}` | Obtener usuario por ID | ADMIN |
| 12 | PUT | `/api/usuarios/{id}` | Actualizar usuario | ADMIN |
| 13 | DELETE | `/api/usuarios/{id}` | Eliminar usuario | ADMIN |
| 14 | POST | `/api/personas` | Crear persona | ADMIN / OPERADOR |
| 15 | GET | `/api/personas` | Obtener todas las personas | ADMIN / OPERADOR |
| 16 | GET | `/api/personas/{id}` | Obtener persona por ID | ADMIN / OPERADOR |
| 17 | PUT | `/api/personas/{id}` | Actualizar persona | ADMIN / OPERADOR |
| 18 | DELETE | `/api/personas/{id}` | Eliminar persona | ADMIN / OPERADOR |
| 19 | POST | `/api/ingresos` | Registrar ingreso de persona | OPERADOR |
| 20 | GET | `/api/ingresos` | Accesos activos en todas las sedes | ADMIN |
| 21 | GET | `/api/ingresos/mi-sede` | Personas activas en la sede del operador | OPERADOR |
| 22 | POST | `/api/salidas` | Registrar salida de persona | OPERADOR |
| 23 | GET | `/api/indicadores/top-personas` | Top 10 personas con más ingresos | ADMIN / OPERADOR |
| 24 | GET | `/api/indicadores/top-personas/sede/{sedeId}` | Top 10 personas por sede | ADMIN / OPERADOR |
| 25 | GET | `/api/indicadores/primer-ingreso` | Personas con primer ingreso | ADMIN / OPERADOR |
| 26 | GET | `/api/indicadores/ingresos-economicos` | Ingresos económicos del operador | OPERADOR |
| 27 | GET | `/api/indicadores/top-operadores` | Top 3 operadores de la semana | ADMIN |
| 28 | GET | `/api/indicadores/top-sedes-facturacion` | Top 3 sedes por facturación | ADMIN |
| 29 | POST | `/api/notificaciones` | Enviar notificación simulada | ADMIN / OPERADOR |

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
9. **Login como ADMIN** → Consultar `GET /api/indicadores/top-operadores` y `/top-sedes-facturacion`

---

## Modelo Entidad-Relación

![Modelo Entidad-Relación Coworking](MODELO%20ENTIDAD%20RELACION%20COWORKING.jpeg)

---

## Estructura del proyecto

```
src/main/java/com/coworking/coworking_technical_test/
├── config/                  # Configuración OpenAPI/Swagger
├── controllers/             # Controladores REST
├── entities/                # Entidades JPA (Rol, Usuario, Persona, Sede, Ingreso, Historico, Cupon)
├── exceptions/              # Excepciones personalizadas y handler global
├── mappers/                 # Mappers manuales Entity ↔ DTO
├── repositories/            # Repositorios Spring Data JPA
├── security/                # JWT, filtros, configuración de seguridad
├── services/
│   ├── interfaces/          # Interfaces de servicios
│   └── implementations/     # Implementaciones de servicios
└── shared/
    ├── dto/                 # Data Transfer Objects
    ├── request/             # Objetos de petición con validaciones
    ├── responses/           # Objetos de respuesta
    └── util/                # Utilidades
```