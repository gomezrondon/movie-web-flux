package com.gomezrondon.reactive.controller;

import com.gomezrondon.reactive.entity.Movie;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MovieClientController {

    WebClient webClient = WebClient.create("http://localhost:8080");

/*    @GetMapping("/client/retrieve")
    public Flux<Movie> getAllMovies() {
        return webClient.get().uri("/movie/list")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Movie.class)
                .log("Movies in Client project:");
    }

    @GetMapping("/client/retrieve/singlemovie/{id}")
    public Mono<Movie> getMovieById(@PathVariable Integer id) {
        return webClient.get().uri("/movie/{id}",id)
                .retrieve()
                .bodyToMono(Movie.class)
                .log("Movie is:");
    }*/

}
