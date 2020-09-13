package com.gomezrondon.moviewebflux.exclude;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class TestFluxStreamIntegration {

    Logger log = org.slf4j.LoggerFactory.getLogger(TestFluxStreamIntegration.class);
    /***
     *  THE APPLICATION NEED TO BE RUNNING!!
     */

    private final WebClient webClient = WebClient.builder().build();
    private final String baseUrl = "http://localhost:8080/movie";

    @Test
    @DisplayName("Testing infinite /infinite endpoint ")
    void test7()  {


        Flux<Integer> integerFlux = webClient.get()
                .uri(baseUrl+"/infinite")
                .retrieve()
                .bodyToFlux(Integer.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
                .doOnError(IOException.class, e -> log.error(e.getMessage()));

        StepVerifier.create(integerFlux)
                .expectNext(0, 1, 2, 3, 4)
                .thenCancel()
                .verify();
    }


    @Test
    @DisplayName("Testing /fluxstream endpoint V2")
    void test6()  {
        EntityExchangeResult<List<Integer>> result = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/movie/fluxstream")
                .accept(MediaType.valueOf(MediaType.APPLICATION_STREAM_JSON_VALUE))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();


        //  log.info(String.valueOf(block));

        Assertions.assertEquals(List.of(0, 1, 2, 3, 4), result.getResponseBody());
    }

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
