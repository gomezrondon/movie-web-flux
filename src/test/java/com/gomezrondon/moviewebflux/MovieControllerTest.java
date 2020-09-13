package com.gomezrondon.moviewebflux;


import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
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
import reactor.core.publisher.Flux;

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
