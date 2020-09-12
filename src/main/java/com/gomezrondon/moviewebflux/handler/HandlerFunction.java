package com.gomezrondon.moviewebflux.handler;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class HandlerFunction {

    private final MovieService service;

    public HandlerFunction(MovieService service) {
        this.service = service;
    }


    @NotNull
    public Mono<ServerResponse> getFluxStream2(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(getInfinite(), Integer.class );
    }

    @NotNull
    private Flux<Integer> getInfinite() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue)
                .log();
    }


    @NotNull
    public Mono<ServerResponse> getFluxStream(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(getLimitedFlux(), Integer.class );
    }

    @NotNull
    private Flux<Integer> getLimitedFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue)
                .take(5)
                .log();
    }

    @NotNull
    public Mono<ServerResponse> getAllMovies(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(service.findAll(), Movie.class );
    }

    @NotNull
    public Mono<ServerResponse> getMovieByID(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(service.findById(Integer.parseInt(serverRequest.pathVariable("id"))), Movie.class );
    }

    @NotNull
    public Mono<ServerResponse> getMovieByTitle(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(getMovieByTitle(serverRequest.pathVariable("title")), Movie.class );

    }

    public Mono<Movie> getMovieByTitle(String title) {
        return service.findByTitle(title)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")));
    }

    @NotNull
    public Mono<ServerResponse> save(ServerRequest serverRequest) {

        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(serverRequest.bodyToMono(Movie.class).flatMap(service::save), Movie.class );
    }
}
