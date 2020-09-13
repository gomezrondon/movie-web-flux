package com.gomezrondon.moviewebflux;


import com.gomezrondon.moviewebflux.entity.Movie;
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


@SpringBootTest
@ExtendWith(SpringExtension.class)//@RunWith() // deprecated
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("dev")
public class MovieControllerTest {

    @Autowired
    private WebTestClient webTestClient;



    private final String baseUrl = "http://localhost:8080/movie";

/*
// I Already implemente the mock data in ConfigApplication.class

    @Autowired
    private MovieService service;

    public static List<Movie> data() {
        return List.of("Matrix", "Terminator", "RoboCop", "Alien II", "RoboCop2", "Batman Begins ", "Matrix 2", "Transformers", "Limitless")
                .stream().map(title -> new Movie(null, title)).collect(Collectors.toList());
    }

    @BeforeEach
    void setup() {
        service.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(service::save)
                .doOnNext(movie -> {
                    System.out.println("Inserted movie is: " + movie);
                }).blockLast();
    }
*/


    @Test
    public void getAllItems() {
        webTestClient.get().uri(baseUrl+"/list")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(Movie.class)
                .hasSize(9);
    }


}
