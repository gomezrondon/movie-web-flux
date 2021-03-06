package com.gomezrondon.moviewebflux.router;


import com.gomezrondon.moviewebflux.handler.HandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;


@Configuration
public class RouterFunctionConfig {


    @Bean  //Version 2 of how to write it
    RouterFunction<?> routes(HandlerFunction handlerFunction){
        return RouterFunctions
                .route(GET("/movie/infinite"), handlerFunction::getFluxStream2)
                .andRoute(GET("/movie/fluxstream"), handlerFunction::getFluxStream)
                .andRoute(GET("/movie/list"), handlerFunction::getAllMovies)
                .andRoute(GET("/movie/runtimeexception"), handlerFunction::getRuntimeException)
                .andRoute(GET("/movie/{id}"), handlerFunction::getMovieByID)
                .andRoute(GET("/movie/title/{title}"), handlerFunction::getMovieByTitle)
                .andRoute(POST("/movie"), handlerFunction::save)
                .andRoute(POST("/movie/batch"), handlerFunction::batchSave)
                .andRoute(PUT("/movie"), handlerFunction::update)
                .andRoute(DELETE("/movie/{id}"), handlerFunction::delete);
    }



/*    @Bean //Version 1 of how to write it
    RouterFunction<?> routes(HandlerFunction handlerFunction){
        return nest(path("/movie"),
                route(GET("/infinite"), handlerFunction::getFluxStream2)
                .and(route(GET("/fluxstream"), handlerFunction::getFluxStream))
        );
    }*/
}
