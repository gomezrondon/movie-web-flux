package com.gomezrondon.reactive.router;


import com.gomezrondon.reactive.handler.HandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class MovieClientRouterFunction {

    @Bean
    RouterFunction<?> routes(HandlerFunction handlerFunction){
        return nest(path("/client/retrieve"),
                route(GET(""), handlerFunction::getRetrieve)
                        .and(route(GET("/singlemovie/{id}"), handlerFunction::getSingleMovie))
        );
    }
}
