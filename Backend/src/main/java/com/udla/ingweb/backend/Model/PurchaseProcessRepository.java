package com.udla.ingweb.backend.Model;

import com.udla.ingweb.backend.Entity.PurchaseProcess;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PurchaseProcessRepository extends MongoRepository<PurchaseProcess,String> {
}
