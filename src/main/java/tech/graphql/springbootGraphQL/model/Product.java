package tech.graphql.springbootGraphQL.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {

  private Integer id;
  private String name;
  private Integer price;
  private Category category;

}
