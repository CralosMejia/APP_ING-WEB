package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Entity.User;

import java.util.Map;

public interface ProductController {

    public Map<String, Object> createProduct(Product product);

    public Map<String, Object> getProductsCustomer();
    public Map<String, Object> getProductsStore(String idStore);

    public Map<String, Object> findProducts(String findParam,String userId);
    public Map<String, Object>  updateProduct(String id, Product product);

    public Map<String, Object> deleteProduct(String id);
}
