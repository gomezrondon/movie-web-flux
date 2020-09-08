package com.gomezrondon.moviewebflux.controller;


import com.gomezrondon.moviewebflux.entity.Movie;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;

@Log4j2

public class WebClientMovieClient {
    private final WebClient webClient;

    public WebClientMovieClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Movie> movieByTitle(String title) {
        return webClient.get()
                .uri("http://localhost:8080/movie/title/{title}", title)
                .retrieve()
                .bodyToMono(Movie.class)
                //.retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20)) // deprecated
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5) ))
                .doOnError(IOException.class, e -> log.error(e.getMessage()));
    }
}
