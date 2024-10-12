package tech.graphql.springbootGraphQL.controller;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import tech.graphql.springbootGraphQL.model.Category;
import tech.graphql.springbootGraphQL.model.Product;

@Controller
public class ProductController {

  @QueryMapping
  public Flux<Product> products () {
    var p1 = Product.builder ()
        .id (new Random ().nextInt (10000, 30000))
        .price (new Random ().nextInt (100, 200))
        .name ("Test Product 1")
        .category (Category.builder ().id (UUID.randomUUID ()).name ("PROMO-1").build ())
        .build ();

    var p2 = Product.builder ()
        .id (new Random ().nextInt (30000, 50000))
        .price (new Random ().nextInt (100, 200))
        .name ("Test Product 2")
        .category (Category.builder ().id (UUID.randomUUID ()).name ("PROMO-2").build ())
        .build ();

    return Flux.fromIterable (List.of (p1, p2));
  }
}
