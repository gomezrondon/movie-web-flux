package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.Movie;
import reactor.core.publisher.Flux;

public interface MovieService {

    public Flux<Movie> getAllMovies();

}
