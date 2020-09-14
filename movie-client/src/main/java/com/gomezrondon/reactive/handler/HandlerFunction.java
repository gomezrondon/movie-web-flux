package com.gomezrondon.reactive.handler;


import com.gomezrondon.reactive.controller.MovieClientController;
import com.gomezrondon.reactive.entity.Movie;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class HandlerFunction {

    WebClient webClient = WebClient.create("http://localhost:8080");


    public Mono<ServerResponse> getRetrieve(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(this.getAllMovies(), Movie.class );
    }

    public Flux<Movie> getAllMovies() {
        return webClient.get().uri("/movie/list")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Movie.class)
                .log("Movies in Client project:");
    }

    public Mono<ServerResponse> getSingleMovie(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(getMovieById(Integer.parseInt(serverRequest.pathVariable("id"))), Movie.class );
    }

    public Mono<Movie> getMovieById(Integer id) {
        return webClient.get().uri("/movie/{id}",id)
                .retrieve()
                .bodyToMono(Movie.class)
                .log("Movie is:");
    }
}
