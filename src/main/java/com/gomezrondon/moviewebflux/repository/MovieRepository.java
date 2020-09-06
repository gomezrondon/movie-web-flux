package com.gomezrondon.moviewebflux.repository;

import com.gomezrondon.moviewebflux.entity.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, Integer> {

    Mono<Movie> findById(int id);
}
