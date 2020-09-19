package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.MovieMongo;
import com.gomezrondon.moviewebflux.repository.RepositoryMongoMovies;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Profile("mongo")
public class MovieMongoServiceImp implements MovieMongoService {

    private final RepositoryMongoMovies repository;

    public MovieMongoServiceImp(RepositoryMongoMovies repository) {
        this.repository = repository;
    }

    @Override
    public Flux<MovieMongo> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<MovieMongo> findById(String id) {
        return repository.findById(id).log(">>>findById: ");
    }

    @Override
    public Mono<MovieMongo> save(MovieMongo movie) {
        return repository.save(movie);
    }

    @Override
    public Mono<MovieMongo> insert(MovieMongo movie) {
        return repository.insert(movie);
    }


    @Override
    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }


}
