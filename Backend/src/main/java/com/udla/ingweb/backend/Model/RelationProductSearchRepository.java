package com.udla.ingweb.backend.Model;

import com.udla.ingweb.backend.Entity.RelationProductSearch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RelationProductSearchRepository extends MongoRepository<RelationProductSearch,String> {
}
