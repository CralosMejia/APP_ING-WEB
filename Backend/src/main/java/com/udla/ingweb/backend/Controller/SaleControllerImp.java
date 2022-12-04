package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Entity.Sale;
import com.udla.ingweb.backend.Entity.Store;
import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.ProductRepository;
import com.udla.ingweb.backend.Model.SaleRepository;
import com.udla.ingweb.backend.Model.StoreReposiroty;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class SaleControllerImp implements SaleController{

    @Autowired
    private SaleRepository saleRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private StoreReposiroty storeRepo;

    @Override
    public Map<String, Object> getSales(String userId) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        List<Sale> salesSave = saleRepo.findAll().stream().filter(sale -> sale.getUserId().equals(userId)).toList();



        respJson.put("Sales",salesSave);
        return respJson;
    }

    @Override
    public Map<String, Object> getSalesProd(String prodId) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        List<Sale> salesSave = saleRepo.findAll().stream().filter(sale -> sale.getProducts().stream().anyMatch(product -> product.getId().equals(prodId))).toList();
        salesSave.forEach(sale -> {
            sale.getProducts().removeIf(product -> !product.getId().equals(prodId));
        });

        respJson.put("Sales",salesSave);
        return respJson;
    }

    @Override
    public void createSale(String userId, List<Product> productsShoppingCar) {

        List<Product> products= new ArrayList<Product>(productRepo.findAll());
        AtomicReference<Float> total = new AtomicReference<>((float) 0);
        AtomicInteger limitAmount= new AtomicInteger();


        productsShoppingCar.forEach(product ->{
            String id = product.getId();
            Product pro = productRepo.findById(id).get();
            limitAmount.set(pro.getAmount());

            if(limitAmount.get() != 0){
                if(product.getAmount() > limitAmount.get()) {
                    product.setAmount(limitAmount.get());
                }

                int diff = limitAmount.get() - product.getAmount();

                pro.setAmount(diff);
                product.setAmount(product.getAmount());

                productRepo.save(pro);

                total.set(total.get() + (product.getPrice() * product.getAmount()));
            }
        } );

        if(limitAmount.get() != 0){
            Sale sale = new Sale(userId, total.get(),productsShoppingCar);
            saleRepo.save(sale);

        }

    }

    private void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }
}
