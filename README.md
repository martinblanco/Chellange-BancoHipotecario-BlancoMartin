# Prueba Técnica - Quarkus - Blanco Martin

Este proyecto es un ejemplo de integración con la API [JSONPlaceholder](https://jsonplaceholder.typicode.com) usando **Quarkus**. Utilizando un Aquitectura en Capas: 
-Capa de Presentacion donde Expone Enpoints para consumir la API.
-Capa de Servicio que implementa la logica de negocio, transforma los datos y coordina las llamadas a la API.
-Capa de Integracion que consume la API externa (JSONPlaceholder) mediante REST Client.
-Capa de Datos (DTOs) que define los modelos de datos que se usan entre las capas, los estandarisa y genera integridad de los mismos.

Decisiones Técnicas:
-Quarkus como framework por su soporte nativo de REST
-Arquitectura en capas para separar las responsabilidades y tener un codigo limpio e legible
-Manejo de exepciones centralizado para tener respuestas iguales a errores consistentes
-Configuracion externa para definir parametros (como maxPosts y maxComments) y tener mas control sin modificar el codigo

Requisitos:
-Java 21 JDK
-Maven 3.9+
-Conexión a internet para la API externa

Opcional:
-IDE Recomendado: Eclipse 2023-09 R

Configuracion (Archivo "application.properties"):
-Configuracion del REST client:
	jsonplaceholder-api/mp-rest/url=https://jsonplaceholder.typicode.com
-Configuracion de maximo de post y comentarios a mostrar:
	app.max-posts=5
	app.max-comments=4

Compilacion:
-Compilar y ejecutar con test en modo dev desde la terminal: **mvn clean install quarkus:dev**
o sin test: **mvn clean install -DskipTests quarkus:dev**
-Esto inicia la aplicacion en: **http://localhost:8080**

Endpoints:
-GET /posts (Ej: GET http://localhost:8080/post):
	retorna post limitados segun **app.max-posts** y cada post incluye datos del autor y comentarios limitqdos por **app.max-comments**
	Ejemplo en curl: curl.exe -X GET http://localhost:8080/post

-DELETE /post/{id} (Ej: DELETE http://localhost:8080/post/10)
	Elimina el post indicado por id en {id}
	Ejemplo en curl: curl.exe -X DELETE http://localhost:8080/post/132323432 -i

-Ejecutar solo Pruebas: mvn test