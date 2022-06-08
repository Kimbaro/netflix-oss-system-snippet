package com.kimbaro.eureka.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Service
public class JwtGlobalFilter extends AbstractGatewayFilterFactory<JwtGlobalFilter.Config> {
    final Logger logger = LoggerFactory.getLogger(JwtGlobalFilter.class);

    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;

        public Config(String baseMessage, boolean preLogger, boolean postLogger) {
            this.baseMessage = baseMessage;
            this.preLogger = preLogger;
            this.postLogger = postLogger;
        }
    }


    @Override
    public GatewayFilter apply(JwtGlobalFilter.Config config) {
        return (exchange, chain) -> {
            try {
                String token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
            } catch (NullPointerException e) {
                logger.warn("no token.");
                exchange.getResponse().getHeaders().set("status", "401");
                logger.info("status code :" + exchange.getResponse().getStatusCode());
                return chain.filter(exchange);
            }
            return chain.filter(exchange);
        };
    }
}
