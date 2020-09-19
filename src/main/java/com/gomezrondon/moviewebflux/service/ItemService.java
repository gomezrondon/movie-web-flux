package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.ItemCapped;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {

    Flux<ItemCapped> findAll();

    Mono<ItemCapped> save(ItemCapped movie);

    Mono<Void> deleteAll();
}
