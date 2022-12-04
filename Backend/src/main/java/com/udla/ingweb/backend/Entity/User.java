package com.udla.ingweb.backend.Entity;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document(collection =  "usuarios")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String ROL = "USER";
    private List<Product> shoppingCar = new ArrayList<>();
//    private List<Sale> productsPurchased = new ArrayList<>();
//    private List<Store> stores = new ArrayList<>();


    public void addProdShoppingCar(Product product){
        shoppingCar.add(product);
    }
    public void deleteProdShoppingCar(Product product){
        Optional<Product> prod= shoppingCar.stream().filter(product1 -> product1.getId().equals(product.getId())).findFirst();
        shoppingCar.remove(shoppingCar.indexOf(prod.get()));
    }

    public int indexOfProductInShoppingCar(String id){
        if(shoppingCar.isEmpty()){
            return -1;
        }

        Optional<Product> prod= shoppingCar.stream().filter(product1 -> product1.getId().equals(id)).findFirst();

        if(prod.isEmpty()){
            return -1;
        }
        return shoppingCar.indexOf(prod.get());
    }

    public void deletedShoppingCar(){
        shoppingCar.clear();
    }


}
