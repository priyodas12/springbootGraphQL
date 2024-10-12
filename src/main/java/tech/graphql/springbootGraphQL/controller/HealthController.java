package tech.graphql.springbootGraphQL.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class HealthController {

  @QueryMapping
  public Mono<Map<String,String>> ping (){
    var healthInfo=
        (UUID.randomUUID ().toString ().concat ("-").concat (LocalDateTime.now ().toString ()));
    return Mono.just (Map.of ("HealthInfo", healthInfo));
  }
}
