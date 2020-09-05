package com.gomezrondon.moviewebflux.repository;

import com.gomezrondon.moviewebflux.entity.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends ReactiveCrudRepository<Movie, Integer> {
}
