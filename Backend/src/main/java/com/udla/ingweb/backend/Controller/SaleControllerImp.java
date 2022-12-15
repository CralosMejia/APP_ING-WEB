package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.*;
import com.udla.ingweb.backend.Entity.Interfaces.Products.BestSellProduct;
import com.udla.ingweb.backend.Model.ProductRepository;
import com.udla.ingweb.backend.Model.RelationProductSearchRepository;
import com.udla.ingweb.backend.Model.SaleRepository;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class SaleControllerImp implements SaleController{

    @Autowired
    private SaleRepository saleRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private RelationProductSearchRepository rpsRepo;

    @Override
    public Map<String, Object> getSales(String userId) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        List<Sale> salesSave = saleRepo.findAll().stream().filter(sale -> sale.getUserId().equals(userId)).toList();



        respJson.put("Sales",salesSave);
        return respJson;
    }

    @Override
    public Map<String, Object> getRelation(String productId) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        List<RelationProductSearch> rpsList = rpsRepo.findAll();
        rpsList.forEach(relationProductSearch -> {
            if(relationProductSearch.getIdProduct().equals(productId)){
                respJson.put("ParamSearch",relationProductSearch.getListSearchParams());
            }
        });
        
        return respJson;
    }

    @Override
    public Map<String, Object> getBestSellingProduct(Date startDate) {
        Date endDate= new Date();


        Map<String, Object> respJson = new HashMap<String,Object>();
        List<Sale> salesSave = saleRepo.findAll().stream().filter(sale -> (sale.getDate().compareTo(startDate) >=0  && sale.getDate().compareTo(endDate) <= 0)
        ).toList();

        List<BestSellProduct> bsp = new ArrayList<>();
        AtomicBoolean aux= new AtomicBoolean(false);
        salesSave.forEach(sale -> {
            sale.getProducts().forEach(product -> {
                if(bsp.isEmpty()) {
                    float sell = product.getAmount() * product.getPrice();
                    bsp.add(new BestSellProduct(product.getName(), sale.getId(), sell));
                }else {
                    aux.set(true);
                    AtomicReference<Float> sellPro= new AtomicReference<>((float) 0);
                    bsp.forEach(bestSellProduct -> {
                        if(bestSellProduct.getId().equals(product.getId())){
                            bestSellProduct.setTotal(bestSellProduct.getTotal()+(product.getAmount() * product.getPrice()));
                            aux.set(false);
                        }
                    });
                    if(aux.get()){
                        sellPro.set(product.getAmount() * product.getPrice());
                        bsp.add(new BestSellProduct(product.getName(), sale.getId(), sellPro.get()));
                    }
                }
            });
        });


        respJson.put("BestSellProduct",getMasValueBestSeller(bsp));
        return respJson;
    }

    private BestSellProduct getMasValueBestSeller(List<BestSellProduct> bsp){
        return bsp.stream().max(Comparator.comparingDouble(BestSellProduct::getTotal)).get();
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
