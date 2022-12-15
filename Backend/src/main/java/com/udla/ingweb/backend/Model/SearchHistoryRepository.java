package com.udla.ingweb.backend.Model;

import com.udla.ingweb.backend.Entity.SearchHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchHistoryRepository extends MongoRepository<SearchHistory,String> {
}
