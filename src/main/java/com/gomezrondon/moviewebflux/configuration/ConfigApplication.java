package com.gomezrondon.moviewebflux.configuration;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import io.r2dbc.spi.ConnectionFactory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class ConfigApplication {

    @Bean
    @Profile("prod")
    CommandLineRunner bootCLRProd(){
        return args -> {
            System.out.println("This is Production!");
        };
    }

    @Bean
    @Profile("test")
    CommandLineRunner bootCLRTest(){
        return args -> {
            System.out.println("This is TEst!");
        };
    }

    @Bean
    @Profile("dev")
    ApplicationRunner applicationRunner(DatabaseClient dbc, MovieService service) {
        return args -> {


            Flux<Movie> firstMovie = insertMovieFlux(service, List.of("Matrix"));

            List<String> movieList = List.of( "Terminator", "RoboCop", "Alien II", "RoboCop2", "Batman Begins ", "Matrix 2", "Transformers", "Limitless");
            Flux<Movie> movieFlux = insertMovieFlux(service, movieList);

            Mono<Integer> dropTable = dbc.execute("DROP TABLE IF EXISTS movie;")
                    .fetch()
                    .rowsUpdated();

            Mono<Integer> createTable = dbc.execute("create table movie (  id   int auto_increment  PRIMARY KEY,  name varchar(50) not null );")
                    .fetch()
                    .rowsUpdated();

            dropTable.log(">>>> drop table >>> ")
                    .then(createTable)
                    .thenMany(service.deleteAll())    // delete all records
                    .thenMany(firstMovie)             // guaranty to be the first
                    .thenMany(movieFlux)             // insert all records
                    .doOnNext(System.out::println)
                    .blockLast(); // to allow records to be inserted before running test

        };
    }

    @NotNull
    private Flux<Movie> insertMovieFlux(MovieService service, List<String> movieList) {
        return Flux.fromIterable(movieList)
                .map(String::toLowerCase)
                .map(Movie::new)
                .flatMap(movie -> {
                    if (movie.getName().equals("matrix")) {
                        return service.insert(movie.withId(1));
                    } else {
                        return service.save(movie);
                    }
                });
    }


}// fin de Config
