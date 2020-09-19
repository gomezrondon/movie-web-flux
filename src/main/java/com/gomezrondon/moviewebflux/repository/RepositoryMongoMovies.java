package com.gomezrondon.moviewebflux.repository;


import com.gomezrondon.moviewebflux.entity.MovieMongo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
 import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryMongoMovies extends ReactiveMongoRepository<MovieMongo, String> {

}
