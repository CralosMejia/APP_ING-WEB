package com.udla.ingweb.backend.Model;


import com.udla.ingweb.backend.Entity.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreReposiroty extends MongoRepository<Store,String> {
}
