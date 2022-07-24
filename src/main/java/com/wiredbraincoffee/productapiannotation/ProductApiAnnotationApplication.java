package com.wiredbraincoffee.productapiannotation;

import com.wiredbraincoffee.productapiannotation.model.Product;
import com.wiredbraincoffee.productapiannotation.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ProductApiAnnotationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiAnnotationApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ProductRepository repository) {
        return args -> {
            repository.save(new Product(null, "wcw", 2.99)).subscribe(System.out::println);
            repository.saveAll(Flux.just(
                    new Product(null, "Latte", 2.99),
                    new Product(null, "Decaf", 1.99),
                    new Product(null, "Capp", 4.99))).subscribe(System.out::println);;


            /*Flux.just(
                            new Product(null, "Latte", 2.99),
                            new Product(null, "Decaf", 1.99),
                            new Product(null, "Capp", 4.99))
                    .flatMap(p -> repository::save).thenMany(repository.findAll()).subscribe(System.out::println);*/


            // productFlux.thenMany(repository.findAll()).subscribe(System.out::println);
        };
    }
}