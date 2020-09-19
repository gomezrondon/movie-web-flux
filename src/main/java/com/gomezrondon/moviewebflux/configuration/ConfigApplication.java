package com.gomezrondon.moviewebflux.configuration;


import com.gomezrondon.moviewebflux.entity.ItemCapped;
import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.ItemService;
import com.gomezrondon.moviewebflux.service.MovieMongoService;


import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;


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

//-------------- mongo Reactive --------------

    @Bean
    ApplicationRunner movieMongoApplicationRunner2(MovieMongoService service) {
        return args -> {

            Flux<Movie> firstItem = insertMovieMongoFlux(service, List.of("Matrix"));

            List<String> movieList = List.of( "Terminator", "RoboCop", "Alien II", "RoboCop2", "Batman Begins ", "Matrix 2", "Transformers", "Limitless");
            Flux<Movie> itemsFlux = insertMovieMongoFlux(service, movieList);

            service.deleteAll()    // delete all records
                    .thenMany(firstItem)             // guaranty to be the first
                    .thenMany(itemsFlux)             // insert all records
                    .thenMany(service.findAll())
                    .doOnNext(System.out::println)
                    .blockLast();

        };
    }

    @NotNull
    private Flux<Movie> insertMovieMongoFlux(MovieMongoService service, List<String> movieList) {
        return Flux.fromIterable(movieList)
                .map(String::toLowerCase)
                .map(Movie::new)
                .flatMap(service::save);
    }

/*
    @Bean
    ApplicationRunner mongoApplicationRunner(ItemService service) {
        return args -> {

            Flux<ItemCapped> firstItem = insertItemsFlux(service, List.of("Matrix"));

            List<String> movieList = List.of( "Terminator", "RoboCop", "Alien II", "RoboCop2", "Batman Begins ", "Matrix 2", "Transformers", "Limitless");
            Flux<ItemCapped> itemsFlux = insertItemsFlux(service, movieList);

            service.deleteAll()    // delete all records
                    .thenMany(firstItem)             // guaranty to be the first
                    .thenMany(itemsFlux)             // insert all records
                    .doOnNext(System.out::println)
                    .blockLast();

        };
    }
*/


    @NotNull
    private Flux<ItemCapped> insertItemsFlux(ItemService itemService, List<String> itemList) {
        return Flux.fromIterable(itemList)
                .map(String::toLowerCase)
                .map(description -> new ItemCapped(null,description, 99.9))
                .flatMap(item -> {
                    if (item.getDescription().equals("matrix")) {
                        return itemService.save(item);
                    } else {
                        return itemService.save(item);
                    }
                });
    }

/*

    //---------------- mydql r2dbc -----------------

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
*/


}// fin de Config
