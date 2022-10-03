package com.udla.ingweb.backend.repository;

import com.udla.ingweb.backend.documents.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {
}
