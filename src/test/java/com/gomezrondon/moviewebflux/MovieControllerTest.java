package com.gomezrondon.moviewebflux;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@ExtendWith(SpringExtension.class)//@RunWith() // deprecated
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class MovieControllerTest  {

    @Autowired
    private WebTestClient webTestClient;


    private final String baseUrl = "http://localhost:8080/movie";



    @Test
    @DisplayName("test endpoint /movie/{id} Not Found")
    public void getByID2() {
        webTestClient.get().uri(baseUrl + "/{id}", 100)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    @DisplayName("test endpoint /movie/{id} ")
    public void getByID() {
        Flux<Movie> flux = webTestClient.get().uri(baseUrl + "/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .returnResult(Movie.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNextCount(1)
                .verifyComplete();
    }


    @Test
    @DisplayName("test endpoint /movie/list v3")
    public void getAllItemsV3() {
        Flux<Movie> flux = webTestClient.get().uri(baseUrl + "/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .returnResult(Movie.class)
                .getResponseBody();

        StepVerifier.create(flux)
                .expectNextCount(9)
                .verifyComplete();
    }


    @Test
    @DisplayName("test endpoint /movie/list v2")
    public void getAllItemsV2() {
        webTestClient.get().uri(baseUrl + "/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(Movie.class)
                .consumeWith(response -> {
                    List<Movie> movieList = response.getResponseBody();
                    movieList.forEach(movie -> {
                        Assertions.assertTrue(movie.getId() != null);
                    });
                });
    }

    @Test
    @DisplayName("test endpoint /movie/list")
    public void getAllItems() {
        webTestClient.get().uri(baseUrl+"/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(Movie.class)
                .hasSize(9);
    }


}
