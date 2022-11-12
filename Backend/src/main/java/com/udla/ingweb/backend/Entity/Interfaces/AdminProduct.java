package com.udla.ingweb.backend.Entity.Interfaces;

import com.udla.ingweb.backend.Entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AdminProduct {

    public Product product;
    public String ID;
}
