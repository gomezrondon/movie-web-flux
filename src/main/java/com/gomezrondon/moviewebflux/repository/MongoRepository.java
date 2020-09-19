package com.gomezrondon.moviewebflux.repository;

import com.gomezrondon.moviewebflux.entity.ItemCapped;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ItemMongoRepository extends ReactiveCrudRepository<ItemCapped, String> {
}
