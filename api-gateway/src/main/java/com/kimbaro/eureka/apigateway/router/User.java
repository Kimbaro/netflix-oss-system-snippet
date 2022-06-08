package com.kimbaro.eureka.apigateway.router;

import com.kimbaro.eureka.apigateway.filter.JwtGlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class User {
    @Bean
    public RouteLocator userApi(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/user/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8883"))
                .build();
    }

    @Bean
    public RouteLocator userAuthApi(RouteLocatorBuilder builder, JwtGlobalFilter jwtFilter) {
        return builder.routes()
                .route(p -> p
                        .path("/auth")
                        .filters(f -> f
                                .filter(jwtFilter.apply(new JwtGlobalFilter.Config("test", true, false)))
                                .circuitBreaker(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8883")
                )
                .build();
    }
}
