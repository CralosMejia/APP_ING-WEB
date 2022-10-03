package com.udla.ingweb.backend.repository;

import com.udla.ingweb.backend.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
