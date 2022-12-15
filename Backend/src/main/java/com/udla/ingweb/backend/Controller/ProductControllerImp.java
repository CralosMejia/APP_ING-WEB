package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Entity.SearchHistory;
import com.udla.ingweb.backend.Entity.Store;
import com.udla.ingweb.backend.Model.ProductRepository;
import com.udla.ingweb.backend.Model.StoreReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ProductControllerImp implements ProductController {


    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private PurchaseProcessController ppControl;
    @Autowired
    private SearchHistoryController shController;


    @Override
    public Map<String, Object> createProduct(Product product) {

        Map<String, Object> respJson = new HashMap<String,Object>();
        Product productSave = productRepo.save(product);

//        Optional<Store> storesave = storeRepo.findById(product.getStoreID());
//        storesave.get().addProduct(product);
//        storeRepo.save(storesave.get());


        respJson.put("Status","OK");
        respJson.put("Product",productSave);

        return respJson;
    }

    @Override
    public Map<String, Object> getProductsCustomer() {
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<Product> products= new ArrayList<Product>(productRepo.findAll());

        products.removeIf(product ->
                (product.getAmount() == 0));

        respJson.put("Products",products);

        return respJson;
    }

    @Override
    public Map<String, Object> getProductsStore(String idStore) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<Product> products= new ArrayList<Product>(productRepo.findAll());

        products.removeIf(product ->
                (!product.getStoreID().equals(idStore)));

        respJson.put("Products",products);

        return respJson;
    }

    @Override
    public Map<String, Object> findProducts(String findParam,String userId) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        List<String> splited = Arrays.stream(findParam.split(" ")).toList();
        List<Product> products= new ArrayList<Product>(productRepo.findAll()) ;
        List<Product> result = new ArrayList<>();

        products.removeIf(product ->
                (product.getAmount() == 0));

        if(!findParam.equals("allproductstobuy")){
            splited.forEach(s -> {
                products.forEach(product -> {
                    if(product.getName().toLowerCase().contains(s.toLowerCase()) || product.getDescription().contains(s.toLowerCase())){

                        if(!result.stream().anyMatch(product1 -> product1.getId().equals(product.getId()))){
                            result.add(product);
                        }
                    }
                });
            });
        }

        if(!result.isEmpty()){
            String idProcess = ppControl.obtainIdProcess(userId);
            SearchHistory sh = new SearchHistory(userId,findParam,result,idProcess);
            shController.createSearchHistory(sh);
        }

        respJson.put("Products",result);
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
        if(product.getAmount() != 0){
            productsave.get().setAmount(product.getAmount());
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
