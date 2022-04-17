package cn.monkey.data.proxy.router;

import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Configuration
public class RouterConfig {
    @Bean
    RouteConfigProperties routeConfigProperties() {
        return new RouteConfigProperties();
    }

    private RuntimeWiring runtimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetcher("hello", new StaticDataFetcher("Query hello word")))
                //.type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("Query hello word")))
                .type("Mutation", builder -> builder.dataFetcher("hello", new StaticDataFetcher("Mutation hello word")))
                .type(newTypeWiring("Query").dataFetcher("book", environment -> {
                    environment.getArguments();
                    return Collections.emptyMap();
                }))
                .build();
    }
}
