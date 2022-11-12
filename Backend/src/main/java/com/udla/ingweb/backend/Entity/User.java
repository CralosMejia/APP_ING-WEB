package com.udla.ingweb.backend.Entity;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection =  "usuarios")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String ROL = "USER";

}
