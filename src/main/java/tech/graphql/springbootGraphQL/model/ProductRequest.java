package tech.graphql.springbootGraphQL.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Data;

@Data
public class ProductRequest {

  private String productName;
  private BigDecimal productPrice;
  private BigInteger categoryId;
}
