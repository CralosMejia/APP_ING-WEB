package com.udla.ingweb.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection =  "stores")
@Getter
@Setter
@RequiredArgsConstructor
public class Store {
    @Id
    private String id;
    private String name;
    private String address;
    private User owner;
    //private List<Product> products;
}
