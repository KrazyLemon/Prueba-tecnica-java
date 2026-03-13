# API de Usuarios - Prueba Técnica Java

API REST desarrollada en **Java + Spring Boot** como parte de una prueba técnica.
La aplicación permite consultar usuarios y filtrarlos mediante una sintaxis simple en la URL.

#### Autor: Angel Eduardo Velazquez Morales

---

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Docker
* Swagger / OpenAPI
* Postman

---
```
Controller → Service → Repository → Model
```
**Controller**
Expone los endpoints REST.

**Service**
Contiene la lógica de negocio.

**Repository**
Simula el acceso a datos.

**Model / DTO**
Representa las estructuras de datos utilizadas en la API.
También incluye un **GlobalExceptionHandler** para manejar errores.
---
## Instalación

Clonar el repositorio

```
git clone https://github.com/KrazyLemon/Prueba-tecnica-java.git
cd Prueba-tecnica-java
```

Compilar el proyecto

```
mvn clean install
```

Realiza las pruebas usando el siguiente workspace de postman

```
https://www.postman.com/j0ola1/workspace/prueba-tecnica
```

---

## Ejecutar la aplicación

```
mvn spring-boot:run
```

La aplicación se ejecutará en

```
http://localhost:8080
```

---

# Documentación de la API

La documentación está disponible en **Swagger UI**

```
http://localhost:8080/swagger-ui/index.html
```

Desde ahí puedes probar todos los endpoints directamente.

---

# Endpoints principales

## Obtener todos los usuarios

```
GET /users
GET /users?sorted=
```
---

## Filtrar usuarios

```
GET /users?filter=campo+operador+valor
```

### Ejemplo

```
GET /users?filter=name+eq+John
```

---

# Operadores disponibles

| Operador | Descripción |
| -------- | ----------- |
| eq       | igual       |
| co       | contiene    |
| sw       | empieza con |
| ew       | termina con |

### Ejemplo

```
/users?filter=email+co+gmail
```

---

# Ejemplo de respuesta

```
[
  {
    "id": 1554766464,
    "name": "John",
    "email": "john@email.com"
    ...
  }
]
```

---

# Manejo de errores

La API devuelve errores con un formato estándar:

```
{
  "timestamp": "2026-03-13T12:58:38.922359"
  "status": 400,
  "message": "Invalid operator",
  "path": "/users"
}
```

---

# Variables de entorno

| Variable   | Descripción                  |
| ---------- | ---------------------------- |
| AES_SECRET | Clave utilizada para cifrado |

---

# Ejecutar con Docker

Construir la imagen

```
docker build -t api-usuarios .
```

Ejecutar el contenedor

```
docker run -p 8080:8080 api-usuarios
```

---

# Ejecutar con Docker Compose

```
docker compose up --build
```

---

