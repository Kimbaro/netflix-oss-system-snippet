package com.kimbaro.eureka.apigateway.router;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Comment {
    @Bean
    public RouteLocator commentApi(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/comment/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8881"))
                .build();
    }
}
