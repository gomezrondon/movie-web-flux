package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.repository.MovieRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieServiceImp implements MovieService {

    private final MovieRepository repository;

    public MovieServiceImp(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Movie> getAllMovies() {
        return repository.findAll().log();
    }

    @Override
    public Mono<Movie> findById(int id) {
        return repository.findById(id).log();
    }
}
