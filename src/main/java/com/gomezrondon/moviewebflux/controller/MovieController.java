package com.gomezrondon.moviewebflux.controller;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/movie")
@Slf4j
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }


/*
// migrated to RouterFunctionConfig
    @GetMapping(value = "/infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getFluxStream2() {
        return getInfinite();
    }


    @NotNull
    private Flux<Integer> getInfinite() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue)
                .log();
    }

    @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getFluxStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue)
                .take(5)
                .log();
    }
*/
    @GetMapping("/list")
    public Flux<Movie> getAllMovies() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Movie> getMovieByID(@PathVariable int id) {
        return service.findById(id);
    }

    @GetMapping("/title/{title}")
    public Mono<Movie> getMovieByID(@PathVariable String title) {
        return service.findByTitle(title)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movie> save(@Valid @RequestBody Movie movie) {
        return service.save(movie);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Movie> batchSave( @RequestBody List<Movie> movies) {
        return service.saveAll(movies);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@Valid @RequestBody Movie movie) {
        return service.update(movie);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable int id) {
        return service.delete(id);
    }
}
