package com.gomezrondon.moviewebflux.handler;


import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class HandlerFunction {

    @NotNull
    public Mono<ServerResponse> getFluxStream2(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(getInfinite(), Integer.class );
    }

    @NotNull
    private Flux<Integer> getInfinite() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue)
                .log();
    }


    @NotNull
    public Mono<ServerResponse> getFluxStream(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(getLimitedFlux(), Integer.class );
    }

    @NotNull
    private Flux<Integer> getLimitedFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Long::intValue)
                .take(5)
                .log();
    }


}
