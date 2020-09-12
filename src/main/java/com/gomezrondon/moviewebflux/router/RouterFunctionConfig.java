package com.gomezrondon.moviewebflux.router;


import com.gomezrondon.moviewebflux.handler.HandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;



@Configuration
public class RouterFunctionConfig {


    @Bean  //Version 2 of how to write it
    RouterFunction<?> routes(HandlerFunction handlerFunction){
        return RouterFunctions
                .route(GET("/movie/infinite"), handlerFunction::getFluxStream2)
                .andRoute(GET("/movie/fluxstream"), handlerFunction::getFluxStream);
    }

/*    @Bean //Version 1 of how to write it
    RouterFunction<?> routes(HandlerFunction handlerFunction){
        return nest(path("/movie"),
                route(GET("/infinite"), handlerFunction::getFluxStream2)
                .and(route(GET("/fluxstream"), handlerFunction::getFluxStream))
        );
    }*/
}
