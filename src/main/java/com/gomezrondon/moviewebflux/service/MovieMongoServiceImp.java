package com.gomezrondon.moviewebflux.service;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.repository.RepositoryMongoMovies;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class MovieMongoServiceImp implements MovieMongoService {

    private final RepositoryMongoMovies repository;

    public MovieMongoServiceImp(RepositoryMongoMovies repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Movie> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Movie> save(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public Mono<Movie> insert(Movie movie) {
        return repository.insert(movie);
    }

    @Override
    public Mono<Void> update(Movie movie) {
        return null;
    }

    @Override
    public Mono<Void> delete(int id) {
        return null;
    }


    @Override
    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }

    @Override
    public Mono<Movie> findByTitle(String roboCop2) {
        return repository.findByTitle(roboCop2).log("findByTitle:>> ");
    }

    @Override
    public Flux<Movie> saveAll(List<Movie> movies) {
        return null;
    }

    @Override
    public Flux<Movie> saveAll(Flux<Movie> movies) {
        return null;
    }


}
