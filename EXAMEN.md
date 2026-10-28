## Taller de Desarrollo de Aplicaciones Móviles

EC: App móvil que consume una API REST en la nube

## I. Instrucciones

- Lea detenidamente cada una de las instrucciones propuestas.

- El examen es individual.

- La duración es de 200 minutos.

- Evitar la copia.

## II. Capacidad

Conoce las tendencias, plataformas y frameworks actuales para el desarrollo de contenidos para dispositivos móviles.

## III. Descripción de la actividad

El examen consiste en desarrollar una API REST para que los ciudadanos denuncien hechos de inseguridad ciudadana ante el serenazgo de una municipalidad, publicarla en Render.com y consumirla desde una aplicación móvil desarrollada en Android Studio.

## 2.1. Funcionalidades: La API tendrá las siguientes funcionalidades:

| Servicio | Tarea |   | Método End Point | Descripción |
| --- | --- | --- | --- | --- |
| Denuncia | Registrar denuncia | POST | denuncias/ | Registra una denuncia de inseguridad (el estado inicia en 1). |
|   | Obtener denuncias | GET | denuncias/?offset=0&limit=5 Obtiene la lista de | las denuncias paginadas. |
|   | Obtener denuncia | GET | denuncias/{id} | Obtiene la denuncia correspondiente para el código consultado. |
|   | Obtener denuncias de un ciudadano | GET | denuncias/ciudadano/{dni} | Obtiene todas las denuncias de un ciudadano. |
|   | Atender denuncia | PUT | denuncias/{id} | Marca la denuncia como atendida por serenazgo (cambia el estado de 1 a 0) indicando su código.

## 2.2. Diagrama de Arquitectura (Denuncia Service)

**Flujo de Comunicación y Tecnologías:**
- `[App Android]` --(HTTPS / Petición)--> `[Render.com: API REST Spring Boot (Docker)]`.
- `[Render.com: API REST Spring Boot (Docker)]` --(JPA)--> `[PostgreSQL / MySQL]`.
- `[Render.com: API REST Spring Boot (Docker)]` --(JSON / Respuesta)--> `[App Android]`[cite: 3].

## 2.3. Recurso

Los datos de la denuncia que se van a gestionar son los siguientes:

| Nombre | Tipo de dato | Propiedad |
| --- | --- | --- |
| id | int | Primary Key, autoincrement |
| dni | varchar(8) | not null |
| fecha | datetime | not null |
| tipo | varchar(30) | not null (ROBO, ASALTO, VANDALISMO, etc.) |
| direccion | varchar(200) | not null |
| descripcion | varchar(255) | not null |
| estado | int | not null, default 1 |

## 2.4. API en Spring Boot

Crear una aplicación en Spring Boot que tenga la siguiente estructura:

- GROUP ID: com.tuApellido

- Artifact: denunciaservice

- package: com.tuApellido.denunciaservice

- Dependencias: Spring Web, Spring Data JPA y el driver de PostgreSQL o MySQL.

- Capas: entity, repository, service y controller.

## 2.5. Despliegue en Render.com

- Cree la base de datos PostgreSQL en Render (o una base MySQL en un proveedor en la nube) y conecte la API mediante variables de entorno. No suba credenciales al repositorio.

- Publique la API como Web Service en Render usando un Dockerfile.

- Pruebe con Postman los 5 endpoints desde la URL pública, por ejemplo: https://denunciaservice-tuapellido.onrender.com/denuncias/

## 2.6. Aplicación móvil en Android Studio

Crear una aplicación en Android Studio (Java o Kotlin) con el package:

com.tuApellido.denunciasapp

La aplicación debe consumir la API publicada en Render (con Retrofit o Volley) y tener:

- Lista de denuncias paginada (RecyclerView) con un botón para cargar los 5 siguientes.

- Formulario para registrar una denuncia.

- Pantalla de detalle de la denuncia con un botón para atenderla.

- Búsqueda de denuncias por DNI del ciudadano.

Ejecución local: también puede desarrollar y consumir la API de manera local (si no logra publicarla en Render, se evaluará con la ejecución local). Desde el emulador use la URL http://10.0.2.2:8080/ y desde un celular físico la IP de su PC en la misma red (ej.: http://192.168.1.20:8080/). Para esto debe agregar en el archivo AndroidManifest.xml:

- El permiso de internet (obligatorio también para Render): <uses-permission android:name="android.permission.INTERNET"/>

- Como la URL local usa HTTP y no HTTPS, el atributo android:usesCleartextTraffic="true" dentro de la etiqueta <application>.

Nota: el plan gratuito de Render suspende el servicio cuando no se usa y la primera petición puede tardar cerca de un minuto. Configure un tiempo de espera adecuado en su cliente HTTP.

## III. Entrega

a. Usted debe subir los siguientes enlaces:

- Link del repositorio de la API (GitHub, GitLab, Bitbucket o Azure DevOps).

- Link del repositorio de la aplicación móvil.

- URL pública de la API en Render (si la ejecutó de manera local, lo demostrará en la presentación).

b. Mostrar y explicar el desarrollo de su examen, ejecutando la app en el emulador o en un celular.


## ESCUELA ACADEMICO PROFESIONAL DE INGENIERIA DE SISTEMAS

## IV. Rúbrica

| Criterio |   | Nivel de desempeño |   |   |
| --- | --- | --- | --- | --- |
|   | Excelente | En desarrollo | En inicio | En inicio |
| Acceso a datos y API | Crea su base de datos y su API en capas (entity, repository, service, controller). Los 5 endpoints funcionan correctamente. (4) | Crea su base de datos y su API en capas. Funcionan los endpoints de obtener y registrar. (3) | Crea su base de datos y su API, pero solo funcionan los endpoints de obtener. (2) | Crea su base de datos, pero la API no funciona correctamente. (1) |
| Despliegue en Render | La API y la base de datos están publicadas en la nube y responden desde la URL pública de Render. (4) | La API está publicada en Render, pero algunos endpoints presentan errores. (3) | Configura el servicio en Render, pero el despliegue falla. (2) | La API solo funciona en local. (1) |
| Aplicación móvil | La app consume la API (en Render o local): lista paginada, registro, detalle con atención y búsqueda por DNI funcionan correctamente. (4) | La app lista y registra denuncias consumiendo la API. (3) | La app solo lista las denuncias desde la API. (2) | Diseña las pantallas, pero no consume la API. (1) |
| Repositorio de su código fuente | El código fuente de la API y de la app se encuentra en sus repositorios remotos. (4) | Ha creado los repositorios remotos, pero no ha subido todos sus archivos. (3) | Ha creado sus repositorios locales. (2) | Solo ha creado sus repositorios remotos. (1) |
| Presentación | Presenta su resultado demostrando dominio del tema y de forma ordenada. (4) | Presenta su resultado de forma ordenada. (3) | Presenta su resultado terminado. (2) | Presenta su resultado, aunque no esté terminado. (1) |
