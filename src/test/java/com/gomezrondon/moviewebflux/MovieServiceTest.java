package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.repository.MovieRepository;
import com.gomezrondon.moviewebflux.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class MovieServiceTest {

    @Autowired
    private MovieService myService;

    @BeforeAll
    static void initAll() {
        log.info("---Inside initAll---");
    }

    @BeforeEach
    void init(TestInfo testInfo) throws InterruptedException {
        System.out.println("Start..." + testInfo.getDisplayName());
        Thread.sleep(1000L); // necessary to allow the data to be inserted
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
