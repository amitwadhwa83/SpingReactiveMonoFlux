package com.wiredbraincoffee.productapiannotation.controller;

import com.wiredbraincoffee.productapiannotation.model.Product;
import com.wiredbraincoffee.productapiannotation.model.ProductEvent;
import com.wiredbraincoffee.productapiannotation.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    //http://localhost:8080/products/
    @GetMapping
    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    //http://localhost:8080/products/priceGreaterThan?priceGreaterThan=2
    @GetMapping("priceGreaterThan")
    public Flux<Product> getAllProductsWithPrice(@RequestParam(required = false) Double priceGreaterThan) {
        return repository.findAll().filter(product -> product.getPrice().compareTo(priceGreaterThan) > 0);//.map(x -> ResponseEntity.ok(x));
    }

    //curl -v http://localhost:8080/products/62dd58f4c12d0e0cf6c05615
    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
        return repository.findById(id).
                map(product -> ResponseEntity.ok(product)).//defaultIfEmpty(ResponseEntity.notFound().build());
                        switchIfEmpty(Mono.error(
                        new RuntimeException("Not found")))
                .doFirst(() -> System.out.println("Product id:" + id))
                .doOnNext(product -> System.out.println("Found:" + product))
                .doOnError(ex -> System.out.println("Something wrong>>>" + ex))
                .doOnTerminate(() -> System.out.println("Finalized"))
                .doFinally(signal -> System.out.println("Finalized with signal:" + signal));
    }

    //curl -v -H "Content-Type: application/json" -d "{ \"name\":\"black tea\",\"price\":1.99}"  http://localhost:8080/products/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    //curl -v -H "Content-Type: application/json" -d "{ \"name\":\"black tea\",\"price\":100.9}" -X PUT http://localhost:8080/products/62dd5bc51837ae5804f30859
    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable(value = "id") String id, @RequestBody Product product) {

        return repository.findById(id).flatMap(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            return repository.save(existingProduct);
        }).map(updateProduct -> ResponseEntity.ok(updateProduct)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //curl -v -X DELETE http://localhost:8080/products/62dd5bc51837ae5804f30859
    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable(value = "id") String id) {
        return repository.findById(id).flatMap(existingProduct ->
                repository.delete(existingProduct).then(Mono.just(ResponseEntity.ok().<Void>build()))
                        .defaultIfEmpty(ResponseEntity.notFound().build()));
    }

    //curl -v -X DELETE http://localhost:8080/products/
    @DeleteMapping
    public Mono<Void> deleteAllProducts() {
        return repository.deleteAll();
    }

    //curl http://localhost:8080/products/events
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductEvent> getProductEvents() {
        return Flux.interval(Duration.ofSeconds(1)).map(val -> new ProductEvent(val, "Product event"));
    }
}