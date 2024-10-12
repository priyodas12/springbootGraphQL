package tech.graphql.springbootGraphQL.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import tech.graphql.springbootGraphQL.model.Product;

@Service
public class ProductQueryService {

  private final ProductService productService;

  public ProductQueryService (ProductService productService) {
    this.productService = productService;
  }

  public Mono<Product> getProductByProductId (BigInteger id) {
    return productService.getProductByProductId (id);
  }
}
