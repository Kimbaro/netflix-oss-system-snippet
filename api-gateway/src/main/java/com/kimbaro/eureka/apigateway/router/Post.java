package com.kimbaro.eureka.apigateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Post {
    @Bean
    public RouteLocator postApi(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/post/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8882"))
                .build();
    }
}