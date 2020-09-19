package com.gomezrondon.moviewebflux.repository;


import com.gomezrondon.moviewebflux.entity.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
 import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface RepositoryMongoMovies extends ReactiveMongoRepository<Movie, String> {

 Mono<Movie> findByTitle(String title);
}
