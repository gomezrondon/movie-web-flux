package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.repository.MovieRepository;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class MovieServiceImp implements MovieService {

    private final MovieRepository repository;

    public MovieServiceImp(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Movie> findAll() {
        return repository.findAll();
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

    @Override
    @Transactional
    public Flux<Movie> saveAll(List<Movie> movies) {
        // con validation a priori
        return Flux.fromIterable(movies)
                .flatMap(this::validateMovie)
                .flatMap(repository::save);
    }


    @Override
    @Transactional
    public Flux<Movie> saveAll(Flux<Movie> movies) {

        return repository.saveAll(movies.flatMap(this::validateMovie));


        // con validation a priori
       // return
    }

    private Flux<Movie> validateMovie(Movie movie) {
        if (StringUtil.isNullOrEmpty(movie.getName())) {
            return Flux.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Name"));
        } else {
            return Flux.just(movie);
        }
    }


}
