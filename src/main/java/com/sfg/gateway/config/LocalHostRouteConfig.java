package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {


    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder){
        //routing any request that comes to the gateway with path:
        ///api/v1/beer/ or /api/v1/beerUpc/ to http://localhost:8080
        //where the beer service is running
        return builder.routes()
                .route(r -> r.path("/api/v1/beer/*", "/api/v1/beer*", "/api/v1/beerUpc/*")
                .uri("http://localhost:8080")
                .id("beer-service"))
                .build();
    }
}
