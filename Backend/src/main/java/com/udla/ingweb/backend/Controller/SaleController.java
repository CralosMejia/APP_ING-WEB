package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;

import java.util.List;
import java.util.Map;

public interface SaleController {
    public void createSale(String userId,List<Product> productsShoppingCar);
    public Map<String, Object> getSales(String userId);
    public Map<String, Object> getSalesProd(String prodId);

}
