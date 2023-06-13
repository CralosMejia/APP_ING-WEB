package com.udla.ingweb.backend.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection =  "products")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String categoria;
    private float price;
    private String storeID;
    private int amount;
}
