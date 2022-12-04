package com.udla.ingweb.backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document(collection =  "stores")
@Getter
@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
//@ConstructorBinding
public class Store {
    @Id
    private String id;
    private String name;
    private String address;
    private String  owner;



//    private List<Product> products;
//
//    public Store(String name, String address, User owner) {
//        this.name = name;
//        this.address = address;
//        this.owner = owner;
//        this.products = new ArrayList<>();
//    }
//
//    public void addProduct(Product product){
//        products.add(product);
//    }
}
