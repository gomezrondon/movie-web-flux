package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.controller.WebClientMovieClient;
import com.gomezrondon.moviewebflux.entity.Movie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


//@SpringBootTest(classes = Application.class) not working
public class WebClientMovieClientIntegrationTest {

    private final WebClient webClient = WebClient.builder().build();

    @Test
    @DisplayName("Should return the movie entity by name")
    public void test1() throws InterruptedException {

        WebClientMovieClient webClientMovieClient = new WebClientMovieClient(webClient);
        Mono<Movie> movie = webClientMovieClient.movieByTitle("matrix");

        Assertions.assertNotNull(movie);
        Assertions.assertTrue(movie.block().getName().equals("matrix"));
    }

}
