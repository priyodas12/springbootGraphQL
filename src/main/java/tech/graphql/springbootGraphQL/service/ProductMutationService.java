package tech.graphql.springbootGraphQL.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import tech.graphql.springbootGraphQL.model.Product;
import tech.graphql.springbootGraphQL.model.ProductRequest;
import tech.graphql.springbootGraphQL.model.ProductResponse;

@Service
public class ProductMutationService {

  private final ProductService productService;

  public ProductMutationService (ProductService productService) {
    this.productService = productService;
  }

  public Mono<ProductResponse> createProduct (ProductRequest productRequest) {
    var randomLong = new Random ().nextLong (1, 1000000);
    var productToSave = Product.builder ()
        .productName (productRequest.getProductName () + " - ".concat (String.valueOf (randomLong)))
        .productPrice (productRequest.getProductPrice ())
        .categoryId (productRequest.getCategoryId ())
        //.marketAvailableDate (LocalDateTime.now (UTC).minusDays (new Random ().nextInt (1, 100)))
        .build ();
    var productResponse = productService.getSavedProductResponse (productToSave);
    productResponse.doOnNext (
        productResponse1 -> System.out.printf ("Product Response: %s%n", productResponse1));
    return productResponse;
  }
}
