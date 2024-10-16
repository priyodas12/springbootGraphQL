package tech.graphql.springbootGraphQL.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Table ("products")
public class Product {

  @Id
  private BigInteger productId;
  private String productName;
  private BigDecimal productPrice;
  private BigInteger categoryId;

}
