package tech.graphql.springbootGraphQL.dao;

import java.math.BigInteger;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import tech.graphql.springbootGraphQL.model.Category;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, BigInteger> {
}
