# Prueba Técnica - Quarkus - Blanco Martin

Este proyecto es un ejemplo de integración con la API [JSONPlaceholder](https://jsonplaceholder.typicode.com) usando **Quarkus**.  

Se implementa una **arquitectura en capas**:

- **Capa de Presentación:** expone endpoints REST para consumir la API.  
- **Capa de Servicio:** implementa la lógica de negocio, transforma los datos y coordina llamadas a la API externa.  
- **Capa de Integración:** consume la API externa (JSONPlaceholder) mediante REST Client.  
- **Capa de Datos (DTOs):** define los modelos de datos usados entre capas, asegurando estandarización e integridad.

---

## Decisiones Técnicas

- **Quarkus como framework:** soporte nativo para REST, CDI, caching y OpenAPI.  
- **Arquitectura en capas:** separación de responsabilidades, código limpio y fácil mantenimiento.  
- **Manejo centralizado de excepciones:** para respuestas HTTP consistentes y claras.  
- **Configuración externalizada:** parámetros como `maxPosts` y `maxComments` definidos en `application.properties` sin tocar el código.

---

## Requisitos

- Java 21 JDK  
- Maven 3.9+  
- Conexión a Internet para consumir la API externa  

**Opcional:**  
- IDE recomendado: Eclipse 2023-09 R

---

## Configuración (`application.properties`)

REST Client:

	jsonplaceholder-api/mp-rest/url=https://jsonplaceholder.typicode.com

	
Configuracion de maximo de post y comentarios a mostrar:

	app.max-posts=5
	app.max-comments=4

## Compilacion

- Compilar y ejecutar con test en modo dev desde la terminal: **mvn clean install quarkus:dev** o sin test: **mvn clean install -DskipTests quarkus:dev**

- Esto inicia la aplicacion en: **http://localhost:8080**


## Endpoints:
- GET /posts (Ej: GET http://localhost:8080/post)
	retorna post limitados segun **app.max-posts** y cada post incluye datos del autor y comentarios limitdos por **app.max-comments**. Ejemplo en curl:

		curl.exe -X GET http://localhost:8080/post
		
- DELETE /post/{id} (Ej: DELETE http://localhost:8080/post/10)
Elimina el post indicado por id en {id}. Ejemplo en curl:

		curl.exe -X DELETE http://localhost:8080/post/132323432 -i

## Ejecutar solo Pruebas
	mvn test
