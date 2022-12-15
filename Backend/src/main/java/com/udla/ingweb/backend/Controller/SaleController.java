package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SaleController {
    public void createSale(String userId,List<Product> productsShoppingCar);
    public Map<String, Object> getSales(String userId);
    public Map<String, Object> getRelation(String productId);
    public Map<String, Object> getBestSellingProduct(Date date);

    public Map<String, Object> getSalesProd(String prodId);

}
