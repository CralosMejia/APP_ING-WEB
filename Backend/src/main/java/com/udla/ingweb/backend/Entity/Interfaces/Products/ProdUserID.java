package com.udla.ingweb.backend.Entity.Interfaces.Products;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProdUserID {
    public String userId;
    public String productId;
    public int amount;

}
