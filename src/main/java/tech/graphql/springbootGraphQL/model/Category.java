package tech.graphql.springbootGraphQL.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {

  private UUID id;
  private String name;
}
