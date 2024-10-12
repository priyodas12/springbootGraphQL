package tech.graphql.springbootGraphQL.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.graphql.springbootGraphQL.model.Category;
import tech.graphql.springbootGraphQL.model.Product;
import tech.graphql.springbootGraphQL.model.ProductRequest;
import tech.graphql.springbootGraphQL.model.ProductResponse;
import tech.graphql.springbootGraphQL.service.ProductMutationService;
import tech.graphql.springbootGraphQL.service.ProductQueryService;

@Controller
public class ProductController {

  private final ProductMutationService productMutationService;
  private final ProductQueryService productQueryService;

  public ProductController (
      ProductMutationService productMutationService,
      ProductQueryService productQueryService
                           ) {
    this.productMutationService = productMutationService;
    this.productQueryService = productQueryService;
  }

  @QueryMapping
  public Flux<ProductResponse> dummyProducts () {
    var c1 =
        Category.builder ().categoryId (BigInteger.valueOf (new Random ().nextLong (1, 10000)))
            .categoryName ("PROMO -1").build ();
    var c2 = Category.builder ().categoryId (BigInteger.valueOf (new Random ().nextLong (1, 10000)))
        .categoryName ("PROMO-2").build ();

    var p1 = ProductResponse.builder ()
        .productId (BigInteger.valueOf (new Random ().nextInt (10000, 30000)))
        .productPrice (BigDecimal.valueOf (new Random ().nextInt (100, 200)))
        .productName ("Test Product 1")
        .category (c1)
        .build ();

    var p2 = ProductResponse.builder ()
        .productId (BigInteger.valueOf (new Random ().nextInt (30000, 50000)))
        .productPrice (BigDecimal.valueOf (new Random ().nextInt (100, 200)))
        .productName ("Test Product 2")
        .category (c2)
        .build ();

    return Flux.fromIterable (List.of (p1, p2));
  }

  @QueryMapping
  public Mono<Product> getProductByProductId (BigInteger id) {
    return productQueryService.getProductByProductId (id);
  }

  @MutationMapping
  public Mono<ProductResponse> createProduct (@Argument ("input") ProductRequest input) {
    return productMutationService.createProduct (input);
  }
}
