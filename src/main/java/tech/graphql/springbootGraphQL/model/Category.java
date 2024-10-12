package tech.graphql.springbootGraphQL.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table ("categories")
public class Category {

  @Id
  private BigInteger categoryId;
  private String categoryName;
}
