package tech.graphql.springbootGraphQL.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponse {

  private BigInteger productId;
  private String productName;
  private BigDecimal productPrice;
  private Category category;
}
