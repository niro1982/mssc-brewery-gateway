package com.sfg.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("google")
@Configuration
public class GoogleConfig {
    //creating a rout example where we redirect the browser from localhost:909/googlesearch to the google.com page
    @Bean
    public RouteLocator googleRoutConfig(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/googlesearch")
                .filters(f -> f.rewritePath("/googlesearch(?<segment>/?.*)", "/${segment}"))
                .uri("https://google.com")
                        .id("google"))
                .build();
    }
}
