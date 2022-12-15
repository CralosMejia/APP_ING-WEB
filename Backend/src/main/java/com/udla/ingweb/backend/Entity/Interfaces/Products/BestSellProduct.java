package com.udla.ingweb.backend.Entity.Interfaces.Products;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class BestSellProduct {

    private String name;
    private String id;
    private float total;

    public BestSellProduct(String name, String id, float total) {
        this.name = name;
        this.id = id;
        this.total = total;
    }
}
