package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ProductControllerImp implements ProductController {


    @Autowired
    private ProductRepository productRepo;


    @Override
    public Map<String, Object> createProduct(Product product) {

        Map<String, Object> respJson = new HashMap<String,Object>();
        Product productSave = productRepo.save(product);

        respJson.put("Status","OK");
        respJson.put("Product",productSave);

        return respJson;
    }

    @Override
    public Map<String, Object> getProducts() {
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<Product> products= new ArrayList<Product>(productRepo.findAll());

        respJson.put("Products",products);

        return respJson;
    }

    @Override
    public Map<String, Object> updateProduct(String id, Product product) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        Optional<Product> productsave = productRepo.findById(id);

        if(!Objects.isNull(product.getName())){
            productsave.get().setName(product.getName());
        }
        if(!Objects.isNull(product.getDescription())){
            productsave.get().setDescription(product.getDescription());
        }
        if(product.getPrice() != 0){
            productsave.get().setPrice(product.getPrice());
        }
        productRepo.save(productsave.get());

        respJson.put("Status","OK");
        respJson.put("Product",productsave);

        return respJson;
    }

    @Override
    public Map<String, Object> deleteProduct(String id) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        productRepo.deleteById(id);
        respJson.put("Status","OK");
        respJson.put("MSG","Product has been deleted");
        return respJson;
    }
}
