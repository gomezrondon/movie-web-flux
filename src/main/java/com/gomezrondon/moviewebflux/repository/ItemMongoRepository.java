package com.gomezrondon.moviewebflux.repository;

import com.gomezrondon.moviewebflux.entity.ItemCapped;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMongoRepository extends ReactiveMongoRepository<ItemCapped, String> {
}
