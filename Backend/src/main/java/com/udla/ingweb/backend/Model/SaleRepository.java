package com.udla.ingweb.backend.Model;

import com.udla.ingweb.backend.Entity.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale,String> {
}
