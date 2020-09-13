package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MovieService {

    Flux<Movie> findAll();

    Mono<Movie> findById(int id);

    Mono<Movie> save(Movie movie);

    Mono<Movie> insert(Movie movie);

    Mono<Void> update(Movie movie);

    Mono<Void> delete(int id);

    Mono<Void> deleteAll();

    Mono<Movie> findByTitle(String title);

    Flux<Movie> saveAll(List<Movie> movies);

    Flux<Movie> saveAll(Flux<Movie> movies);
}
