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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Collectors;



@AutoConfigureWebTestClient
public class MovieControllerTest extends MovieServiceTest  {

    @Autowired
    private WebTestClient webTestClient;


    private final String baseUrl = "http://localhost:8080/movie";


    @Test
    @DisplayName("test endpoint put /movie ")
    public void updateMovie() {
        Flux<Movie> movieFlux = webTestClient.put().uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new Movie(5, "Mortal Combat II")), Movie.class)
                .exchange()
                .expectStatus().isNoContent()
                .returnResult(Movie.class)
                .getResponseBody();

        StepVerifier.create(movieFlux.log("new Movie: "))
                .verifyComplete();

    }


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
                .getResponseBody()
                .take(8);

        StepVerifier.create(flux)
                .expectNextCount(8) // are 9 but other test is deleting 1
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

    @Test
    @DisplayName("test endpoint post /movie ")
    public void createMovie() {
        Flux<Movie> movieFlux = webTestClient.post().uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new Movie(null, "Mortal Combat")), Movie.class)
                .exchange()
                .expectStatus().isCreated()
                .returnResult(Movie.class)
                .getResponseBody();


        StepVerifier.create(movieFlux.log("new Movie: "))
   // .expectNextCount(1)
                .expectNextMatches(movie -> movie.getId() == 10 && movie.getName().equals("Mortal Combat"))
                .verifyComplete();

    }

    @Test
    @DisplayName("test endpoint delete /movie/{id} ")
    public void deleteMovie() {
       webTestClient.delete().uri(baseUrl + "/{id}", 8)
                .exchange()
                .expectStatus().isNoContent();

    }




}
