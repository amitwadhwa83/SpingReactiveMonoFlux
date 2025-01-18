# WiredBrain Coffee Product API

This is a Spring Boot application that provides a reactive API for managing products in a MongoDB database.

## Technologies Used
- Java
- Spring Boot
- Spring Data MongoDB
- Maven
- Reactor

## Endpoints

### Get All Products
- **URL:** `/products`
- **Method:** `GET`
- **Description:** Retrieves all products.
- **Response:** `Flux<Product>`

### Get Products with Price Greater Than
- **URL:** `/products/priceGreaterThan`
- **Method:** `GET`
- **Description:** Retrieves all products with a price greater than the specified value.
- **Query Parameter:** `priceGreaterThan` (Double)
- **Response:** `Flux<Product>`

### Get Product by ID
- **URL:** `/products/{id}`
- **Method:** `GET`
- **Description:** Retrieves a product by its ID.
- **Path Variable:** `id` (String)
- **Response:** `Mono<ResponseEntity<Product>>`

### Create a New Product
- **URL:** `/products`
- **Method:** `POST`
- **Description:** Creates a new product.
- **Request Body:** `Product`
- **Response:** `Mono<Product>`

### Update an Existing Product
- **URL:** `/products/{id}`
- **Method:** `PUT`
- **Description:** Updates an existing product by its ID.
- **Path Variable:** `id` (String)
- **Request Body:** `Product`
- **Response:** `Mono<ResponseEntity<Product>>`

### Delete a Product by ID
- **URL:** `/products/{id}`
- **Method:** `DELETE`
- **Description:** Deletes a product by its ID.
- **Path Variable:** `id` (String)
- **Response:** `Mono<ResponseEntity<Void>>`

### Delete All Products
- **URL:** `/products`
- **Method:** `DELETE`
- **Description:** Deletes all products.
- **Response:** `Mono<Void>`

### Get Product Events
- **URL:** `/products/events`
- **Method:** `GET`
- **Description:** Retrieves a stream of product events.
- **Response:** `Flux<ProductEvent>`
- **Produces:** `text/event-stream`

## Running the Application

To run the application, use the following command:

```bash
mvn spring-boot:run