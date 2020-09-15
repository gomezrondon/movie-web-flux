package com.gomezrondon.moviewebflux.handler;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Component
@Slf4j
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
    public Mono<ServerResponse> getRuntimeException(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(service.findAll()
                        .concatWith(Mono.error(new RuntimeException("RuntimeException Occurred.")))
/*                                .doOnError( e -> log.error("error message: {}",e.getStackTrace())) // registro el error en el log
                        //el programa no exploto
                                .onErrorResume(s ->{ //better way
                                    log.info("inside on Error Resume");
                                    return Mono.empty();
                                })*/
                        , Movie.class ).doOnError( e -> log.error("error message: {}",e.getMessage())) ;
    }

    @NotNull
    public Mono<ServerResponse> getMovieByID(ServerRequest serverRequest) {
        return service.findById(Integer.parseInt(serverRequest.pathVariable("id")))
                .flatMap(movie -> ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                        .body(BodyInserters.fromValue(movie)))
               // .switchIfEmpty(ServerResponse.notFound().build()); // simple response 404 Not Found
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"))); // full response
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


/*    @NotNull // another way of doing it
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<Movie> movieMono = serverRequest.bodyToMono(Movie.class);

        return movieMono.flatMap(movie -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(movie), Movie.class));
    }*/

    @NotNull
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return ServerResponse.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(serverRequest.bodyToFlux(Movie.class).flatMap(service::update), Movie.class );
    }

    @NotNull
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return ServerResponse.status(HttpStatus.NO_CONTENT)
                .body(service.delete(Integer.parseInt(serverRequest.pathVariable("id"))), Movie.class );
    }

    @NotNull
    public Mono<ServerResponse> batchSave(ServerRequest serverRequest) {
        Flux<Movie> movieFlux = serverRequest.bodyToFlux(Movie.class);
        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.saveAll(movieFlux), Movie.class );
    }


}
