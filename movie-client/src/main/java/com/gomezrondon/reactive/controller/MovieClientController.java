package com.gomezrondon.reactive.controller;

import com.gomezrondon.reactive.entity.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class MovieClientController {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping("/client/retrieve")
    public Flux<Movie> getAllMovies() {
        return webClient.get().uri("/movie/list")
                .retrieve()
                .bodyToFlux(Movie.class)
                .log("Movies in Client project:");
    }
}
