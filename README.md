# API EAN Supermercado

API REST para la gestión y consulta de productos de supermercado utilizando códigos EAN. Permite crear, actualizar, eliminar y consultar detalles de productos, así como obtener información desglosada a partir de un EAN.

## Requisitos

- Java 17 o superior
- Maven 3.9.x o superior

## Instalación y Ejecución

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/PauetCrespin/api-ean-supermercado.git
   cd api-ean-supermercado
   ```

2. **Compilar y ejecutar la aplicación**
   ```bash
   cd ean
   ./mvnw spring-boot:run
   ```
   O en Windows:
   ```bat
   mvnw.cmd spring-boot:run
   ```

3. **Acceso a la consola H2 (opcional)**
   - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - Usuario: `supermercado`
   - Contraseña: `Sup3rm3rc@d0`
   - JDBC URL: `jdbc:h2:mem:testdb`

## Endpoints Principales

### Product Details

- **Obtener todos los productos**
  - `GET /api/productdetails`
- **Obtener producto por ID**
  - `GET /api/productdetails/{id}`
- **Obtener producto por EAN**
  - `GET /api/productdetails/ean/{ean}`
- **Crear producto**
  - `POST /api/productdetails/create`
  - Body ejemplo:
    ```json
    {
      "providerCode": "8437008",
      "productCode": "84590",
      "destination": "1"
    }
    ```
- **Actualizar producto**
  - `PUT /api/productdetails/update/{id}`
  - Body igual al de creación.
- **Eliminar producto**
  - `DELETE /api/productdetails/delete/{id}`

### EAN

- **Obtener detalles a partir de un EAN**
  - `GET /api/ean/{ean}`
  - Respuesta ejemplo:
    ```json
    {
      "providerCode": "8437008",
      "productCode": "84590",
      "destination": "Supermercado España"
    }
    ```

## Notas

- Los códigos EAN deben tener 13 dígitos.
- El campo `destination` acepta los siguientes valores:
  - `1-5`: Tiendas supermercado España
  - `6`: Tiendas supermercado Portugal
  - `8`: Almacenes
  - `9`: Oficinas supermercado
  - `0`: Colmenas

## Pruebas

Para ejecutar los tests:
```bash
./mvnw test
```

---
Desarrollado por Pau Crespo.

Este proyecto consiste en una *API* para la *consulta, creación, actualización y eliminación* mediante el *EAN* de productos en relación a un *supermercado*.

Los códigos EAN en los que esta basado mi proyecto tienen el siguiente formato:

• PPPPPPP+NNNNN+D

Los dígitos indicados con P hacen referencia al proveedor que fabrica el producto. 
Los dígitos indicados con N hacen referencia al código del producto dentro del supermercado y el
dígito indicado con D hace referencia a un digito de destino.

En primer lugar he trabajado en *Visual Studio Code* con la versión de *Spring Boot 3.3.2* y con la versión de *Java 17*.

Para la parte sobre el EAN, he creado:
- *Controlador*: EanController.java
- *Modelo*: EanDetails.java
- *Servicio*: EanService.java
- *Excepciones*: InvalidEanException.java y GlobalExceptionHandler.java

Para hacer las llamadas a la consulta del EAN deberemos poner:
- http://localhost:8080/api/ean/{ean}

También se pueden encontrar todas las llamadas con sus casuisticas en la carpeta postman en la colección *Tests calls API EAN*.

El hilo de la llamada lo podremos en contrar desde nuestro controlador EanController.java.

El puerto en mi caso es 8080 ya que es el puerto por defecto y lo tengo defenido en mi application.properties.

La variable *{ean}* es la cuál deberemos de proporcionar con un formato de 13 dígitos, los cuáles hacen referencia al proveedor, al código del producto y al destino.

He creado también los servicios CRUD con sus respectivas validaciones respecto a los valores de campo y también he persistido la información en una base de datos, en mi caso he utilizado H2. 
Para hacer esta parte he creado los siguientes archivos:
- *Controlador*: ProductDetailsController.java
- *Modelo*: ProducDetails.java
- *Servicio*: ProductDetailsService.java y ValidationService.java
- *Excepciones*: ProductNotFoundException.java
- He definido en el *application.properties* la configuración para la base de datos

Las llamadas respecto a los servicios CRUD las podremos encontrar en la carpeta postman en la colección *SERVICIOS CRUD*.

La estructura del *body* para crear o actualizar es la siguiente:

*{*
  *"providerCode": "",*
  *"productCode": "",*
  *"destination": ""*
*}*

Los tres valores tienen su validación individual, respecto a los nombres de los campos tienen la validación propia de spring y dan la llamada correspondiente respecto a si su valor es el correcto o no (*BAD REQUEST*).

En mi caso he añadido también una consulta mediante el *id* del producto en la tabla y también otra mediante el *ean*.

He añadido la conversión del dígito del destino para que en la tabla se guarde el nombre de su respectivo destino.
