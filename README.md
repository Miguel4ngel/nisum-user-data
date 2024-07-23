# Desafio Java EY

El componente entregado, expone una API Rest como interface para realizar operaciones sobre una entidad Usuario (User).

## Configuración Local

Los siguientes puntos o elementos son necesarios para la construccion e implementacion de esta API.

    * Intellij IDEA Community (Recomendado).
    * Gradle
    * Java 17
    * Base de datos (Para ejemplo, se utilizo base de datos H2, pero se puede configurar cualquier base de datos relacional).

## Configuracion Inicial Base de Datos

### Creacion de Esquema

    No es necesaria ninguna configuracion para la inicializacion del esquema. La creacion se realiza automaticamente al 
    iniciar el microservicio.

## Iniciando Microservicio

Una vez  realizada la configuracion inicial de la base de datos,  ejecutar mediante consola:

    * ./gradlew clean build

Una vez finalizado y obtenido el mensaje BUILD SUCCESSFUL

    * gradlew bootRun

Una vez iniciada la aplicacion, se puede consumir el siguiente endpoint mediante curl o herramienta Postman.

## Especificaciones API v1

## Swagger:

    http://localhost:8081/api-user-data/swagger-ui/index.html#

### Autenticacion de usuario.
#### Para realizar cualquier operacion sobre la entidad Usuario, es necesario autenticarse mediante el endpoint /auth/login.
#### El token obtenido, debe ser enviado en el header de todas las peticiones que requieran autenticacion.
#### Las credenciales por defecto vienen incluidas en la collection postman adjunta en la carpeta resources/collection.
### GET: /api-user-data/v1/auth/login

    curl --location --request GET 'http://localhost:8081/auth/login' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "email": "miguel.sanjuanm@gmail.com",
            "password": "admin"

### Creacion de un nuevo usuario.
#### Todas las peticiones para creacion de nuevos usuarios, deben incluir un token valido. Solo usuarios con Rol ADMIN pueden crear nuevos usuarios.
### POST: /api-user-data/v1/user/

    curl --location --request POST 'http://localhost:8081/user/' \
        --header 'Content-Type: application/json' \
        --header 'Authorization: Bearer {tokenObtenido desde /auth/login}
        --data-raw '{
            "name": "Juan Rodriguez",
            "email": "jrod19@live.com",
            "password": "jrodriguez1991",
            "phones": [
                {
                    "number": "1234567",
                    "cityCode": "1",
                    "countryCode": "57"
                }
            ]
    }'



