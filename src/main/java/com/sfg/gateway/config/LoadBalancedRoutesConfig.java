package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfig {

    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder){
        //routing any request that comes to the gateway with path:
        ///api/v1/beer/ or /api/v1/beerUpc/ to http://localhost:8080
        //where the beer service is running
        return builder.routes()
                .route(r -> r.path("/api/v1/beer/*", "/api/v1/beer*", "/api/v1/beerUpc/*")
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(r -> r.path("/api/v1/customers/**")
                        .uri("lb://order-service")
                        .id("order-service"))
                .route(r -> r.path("/api/v1/beer/*/inventory")
                        .filters(f -> f.circuitBreaker(c -> c.setName("inventoryCB")
                                                        .setFallbackUri("forward:/inventory-failover") //the service name of the circuit breaker
                                                        .setRouteId("inv-failover")))
                        .uri("lb://inventory-service")
                        .id("inventory-service"))
                .route(r -> r.path("/inventory-failover/**")
                        .uri("lb://inventory-failover")
                        .id("inventory-failover-service"))
                .build();
    }

    //we only need to use the profile if we have more than 1 and we want to make sure we are using the one we want
    //the uri is    'lb:://' that stands for oad balance and than the name we gave the micro service in the application.properties file
}
