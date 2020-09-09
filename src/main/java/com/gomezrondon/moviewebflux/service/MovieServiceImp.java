package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MovieServiceImp implements MovieService {

    private final MovieRepository repository;

    public MovieServiceImp(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Movie> findAll() {
        return repository.findAll().log();
    }

    @Override
    public Mono<Movie> findById(int id) {

/*        return  repository.findById(id).hasElement()
                .flatMap(isEmpty -> {
                    log.info(">>>>>>>>>>>>>>>>>>>> "+isEmpty);
                    if (isEmpty) {
                        return repository.findById(id);
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
                    }
                });*/

        return  repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")));


    }

    @Override
    public Mono<Movie> save(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public Mono<Void> update(Movie movie) {
        return findById(movie.getId())
                .map(movieFound -> movie.withId(movieFound.getId())) // setId returns void | withId return itself
                .flatMap(repository::save)
                .then();
               //.thenEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(int id) {
        return findById(id).flatMap(repository::delete)
                .then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }

    @Override
    public Mono<Movie> findByTitle(String title) {
        return repository.findByName(title);
    }


}
