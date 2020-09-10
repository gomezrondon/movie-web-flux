package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.entity.Movie;
import com.gomezrondon.moviewebflux.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.test.StepVerifier;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
@Slf4j
public class MovieServiceTest {

    @Autowired
    private MovieService myService;

/*
    @BeforeAll
    static void initAll() {
        log.info("---Inside initAll---");
    //    BlockHound.install(); // there is an issue with mysql r2dbc
    }

    @BeforeEach
    void init(TestInfo testInfo) throws InterruptedException {
        System.out.println("Start..." + testInfo.getDisplayName());
      //  Thread.sleep(2000L); // necessary to allow the data to be inserted
    }
*/

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
                .expectError(ResponseStatusException.class)
                .verify();

    }

    @Test
    @DisplayName("Web Test Client With Server URL")
    public void test2() {
        WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080"  )
                .build()
                .get()
                .uri("/movie/2")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();
    }


}

