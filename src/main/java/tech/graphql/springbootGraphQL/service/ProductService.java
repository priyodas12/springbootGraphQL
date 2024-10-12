package tech.graphql.springbootGraphQL.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import tech.graphql.springbootGraphQL.dao.ProductRepository;
import tech.graphql.springbootGraphQL.model.Category;
import tech.graphql.springbootGraphQL.model.Product;
import tech.graphql.springbootGraphQL.model.ProductResponse;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private DatabaseClient databaseClient;

  public Mono<ProductResponse> getSavedProductResponse (Product product) {

    var category =
        Category.builder ()
            .categoryId (product.getCategoryId ())
            .categoryName (
                "Promo - %d".formatted (System.nanoTime ()))
            .build ();

    return saveCategory (category)
        .flatMap (_ -> saveProduct (product))
        .map (savedProduct -> ProductResponse.builder ()
            .productId (savedProduct.getProductId ())
            .productName (savedProduct.getProductName ())
            .productPrice (savedProduct.getProductPrice ())
            .category (Category.builder ()
                           .categoryId (category.getCategoryId ())
                           .categoryName (category.getCategoryName ())
                           .build ())
            .build ());
  }

  public Mono<Category> saveCategory (Category category) {
    return databaseClient
        .sql ("SELECT COUNT(*) FROM categories WHERE category_id = :categoryId")
        .bind ("categoryId", category.getCategoryId ())
        .map (row -> row.get (0, Integer.class))
        .first ()
        .flatMap (count -> {
          if (count == 0) {
            return databaseClient.sql ("""
                                               INSERT INTO categories (category_id, category_name)
                                               VALUES (:categoryId, :categoryName)
                                                                          
                                           """)
                .bind ("categoryId", category.getCategoryId ())
                .bind ("categoryName", category.getCategoryName ())
                .fetch ()
                .rowsUpdated ()
                .thenReturn (category);
          }
          else {
            return Mono.just (
                category); // Return the existing category instead of throwing an error
          }
        });
  }


  public Mono<Product> saveProduct (Product product) {

    return databaseClient.sql ("SELECT nextval('product_sequence') AS nextId")
        .map (row -> row.get ("nextId", BigInteger.class))
        .first ()
        .flatMap (nextId -> {
          product.setProductId (nextId);
          return databaseClient.sql ("""
                                          INSERT INTO products 
                                          (product_id,
                                          product_name, 
                                          product_price,
                                          category_id) 
                                         VALUES 
                                         (:productId, :productName, :productPrice,:productCategoryId)
                                                          
                                         """)
              .bind ("productId", product.getProductId ())
              .bind ("productName", product.getProductName ())
              .bind ("productPrice", product.getProductPrice ())
              .bind ("productCategoryId", product.getCategoryId ())
              .fetch ()
              .rowsUpdated ();
        })
        .thenReturn (product);
  }

/*  public Mono<Product> getProductByProductId (BigInteger id) {
    return databaseClient.sql ("""
                                    SELECT * FROM products WHERE product_id=:productId
                                   """)
        .bind ("productId", id)
        .map ((row, metadata) ->
                  Product.builder ()
                      .productId (row.get ("product_id", BigInteger.class))
                      .productName (row.get ("product_name", String.class))
                      .productPrice (row.get ("product_price", BigDecimal.class))
                      .productCategory (row.get ("product_category", Category.class))
                      .build ()).first ();

  }*/

  public Mono<Product> getProductByProductId (BigInteger id) {
    return productRepository.findById (id);
  }
}
