package com.gomezrondon.moviewebflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@Slf4j
public class TestFluxStreamIntegration {


    /***
     *  THE APPLICATION NEED TO BE RUNNING!!
     */

    @Test
    @DisplayName("Testing /fluxstream endpoint")
    void test5()  {
        List<Integer> block = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/movie/fluxstream")
                .accept(MediaType.valueOf(MediaType.APPLICATION_STREAM_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody().collectList().block();


      //  log.info(String.valueOf(block));

        Assertions.assertEquals(String.valueOf(block), String.valueOf(block));
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
