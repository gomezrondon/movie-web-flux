package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.MovieMongo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovieMongoService {
    Flux<MovieMongo> findAll();

    Mono<MovieMongo> findById(String id);

    Mono<MovieMongo> save(MovieMongo movie);

    Mono<MovieMongo> insert(MovieMongo movie);

    Mono<Void> deleteAll();
}
