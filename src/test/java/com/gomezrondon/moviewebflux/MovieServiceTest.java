package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;


@SpringBootTest
@ExtendWith(SpringExtension.class)//@RunWith() // deprecated
@DirtiesContext
@ActiveProfiles("dev")
public class MovieServiceTest  {

    @Autowired
    private MovieService myService;


    @Test
    @DisplayName("test find by Name")
    void test5()  {
        StepVerifier.create(myService.findByTitle("RoboCop2"))
                .expectSubscription()
                .expectNextMatches(movie -> movie.getName().equals("RoboCop2".toLowerCase()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing gel all movies service")
    void test1()  {
        myService.findAll()
                .as(StepVerifier::create)
                .expectNextCount(9)
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing gel by id")
    void test3()  {
        myService.findById(1)
                .as(StepVerifier::create)
                .expectNext(Movie.builder().id(1).name("matrix").build())
                .verifyComplete();
    }

    @Test
    @DisplayName("Testing when find by id throw ResponseStatusException")
    void test4()  {

        StepVerifier.create(myService.findById(99))
                .expectSubscription()
                //.expectError(ResponseStatusException.class)
                .expectErrorMessage("404 NOT_FOUND \"Movie not found\"")
                .verify();

    }




}

