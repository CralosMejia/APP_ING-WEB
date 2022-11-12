package com.udla.ingweb.backend.Model;

import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}