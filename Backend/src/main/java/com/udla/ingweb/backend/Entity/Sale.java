package com.udla.ingweb.backend.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.List;

@Document(collection =  "sales")
@Getter
@Setter
public class Sale {
    @Id
    private String id;
    private String userId;
    private Date date = new Date();
    private float total;
    private List<Product> products;

    public Sale(String userId, float total, List<Product> products) {
        this.userId = userId;
        this.total = total;
        this.products = products;
    }
}
