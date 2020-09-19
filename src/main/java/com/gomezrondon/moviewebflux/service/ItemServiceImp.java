package com.gomezrondon.moviewebflux.service;

import com.gomezrondon.moviewebflux.entity.ItemCapped;
import com.gomezrondon.moviewebflux.repository.ItemMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemServiceImp implements ItemService {

    private final ItemMongoRepository repository;


    public ItemServiceImp(ItemMongoRepository repository) {
        this.repository = repository;
    }


    @Override
    public Flux<ItemCapped> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ItemCapped> save(ItemCapped item) {
        return repository.save(item);
    }


    @Override
    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }


}
