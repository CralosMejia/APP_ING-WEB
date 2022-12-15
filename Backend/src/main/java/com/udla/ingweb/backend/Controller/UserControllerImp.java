package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.Product;
import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.ProductRepository;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Model.Security.config.JwtIO;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class UserControllerImp implements UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtIO jwtio;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SaleController saleController;

    @Autowired
    private PurchaseProcessController ppControl;

    @Override
    public Map<String, Object> createUser(User user) {
        Map<String, Object> respJson = new HashMap<String,Object>();
         //Encrypt password
        Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String pasworddHash = argon.hash(2, 1024, 2, user.getPassword());
        user.setPassword(pasworddHash);
        //Create response
        User userSave = userRepo.save(user);
        String tokenJWT = jwtio.generateToken(userSave.getId());
        respJson.put("Status","OK");
        respJson.put("User",userSave);
        respJson.put("token",tokenJWT);
        return respJson;
    }

    @Override
    public Map<String, Object> getUsers(String token) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        String userID = jwtio.getPayload(token);
        List<User> users = new ArrayList<User>(userRepo.findAll());

        Optional<User> findUser = userRepo.findById(userID);
        findUser.get().setPassword("");
        if(!findUser.isEmpty()) {
            users.forEach(user -> user.setPassword(""));

            respJson.put("User Owner",findUser);
            respJson.put("Status","OK");
            respJson.put("Users",users);

            return respJson;
        }
        respJson.put("Status","FAIL");
        return respJson;
    }

    //hay que mejorar
    @Override
    public Map<String, Object>  updateUser(String id, User user){
        Map<String, Object> respJson = new HashMap<String,Object>();
        Optional<User> usersave = userRepo.findById(id);

        if(!Objects.isNull(user.getName())){
            usersave.get().setName(user.getName());
        }
        if(!Objects.isNull(user.getPassword())){
            usersave.get().setPassword(user.getPassword());
        }

        userRepo.save(usersave.get());

        respJson.put("Status","OK");
        respJson.put("User",usersave);

        return respJson;
    }

    @Override
    public Map<String, Object> deleteUser(String id) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        userRepo.deleteById(id);
        respJson.put("Status","OK");
        respJson.put("MSG","User has been deleted");
        return respJson;
    }

    @Override
    public Map<String, Object> getUserRol(String id) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        Optional<User> usersave = userRepo.findById(id);

        respJson.put("Rol",usersave.get().getROL());
        return respJson;

    }

    @Override
    public Map<String, Object> findUser(String id) {
        Map<String, Object> respJson = new HashMap<String,Object>();

        Optional<User> usersave = userRepo.findById(id);

        respJson.put("user",usersave.get());
        return respJson;
    }

    @Override
    public Map<String, Object> addProdShoppingCar(String id, String prodId,int amount) throws errorMessage {
        Map<String, Object> respJson = new HashMap<String,Object>();
        Optional<User> usersave = userRepo.findById(id);
        Optional<Product> productsave = productRepo.findById(prodId);
        int limitAmount = productRepo.findById(prodId).get().getAmount();
        int indexProd = usersave.get().indexOfProductInShoppingCar(prodId);

        if(amount <= 0){
            message("Invalid amount");
        }

        if(amount > limitAmount){
            message("Not enough units in stock");
        }

        if(indexProd == -1){
            productsave.get().setAmount(amount);
            usersave.get().addProdShoppingCar(productsave.get());
        }else{
            int productAmountUser = usersave.get().getShoppingCar().get(indexProd).getAmount();

            if(productAmountUser + amount <= limitAmount){
                usersave.get().getShoppingCar().get(indexProd).setAmount(productAmountUser + amount);
                userRepo.save(usersave.get());
            }else {
                message("Not enough units in stock");
            }
        }

        userRepo.save(usersave.get());


        respJson.put("Msg","Product add correctly to shopping car");
        return respJson;
    }

    @Override
    public Map<String, Object> deleteProdShoppingCar(String id, String prodId,int amount) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        Optional<User> usersave = userRepo.findById(id);
        Optional<Product> productsave = productRepo.findById(prodId);
        int indexProd = usersave.get().indexOfProductInShoppingCar(prodId);
        int productAmountUser = usersave.get().getShoppingCar().get(indexProd).getAmount();

        if( productAmountUser <= amount){
            usersave.get().deleteProdShoppingCar(productsave.get());
            userRepo.save(usersave.get());
        }else{
            usersave.get().getShoppingCar().get(indexProd).setAmount(productAmountUser - amount);
            userRepo.save(usersave.get());
        }

        respJson.put("Msg","Product deleted correctly to shopping car");
        return respJson;
    }

    @Override
    public Map<String, Object> buy(String userID) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        User usersave = userRepo.findById(userID).get();

        if(!usersave.getShoppingCar().isEmpty()){
            ppControl.finishProcess(userID);
            saleController.createSale(userID,usersave.getShoppingCar());
            usersave.deletedShoppingCar();
            userRepo.save(usersave);
            respJson.put("Msg","successful purchase");

        }

        return respJson;
    }

    @Override
    public Map<String, Object> getShoppingCar(String userID) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        User usersave = userRepo.findById(userID).get();

        respJson.put("ShoppingCar",usersave.getShoppingCar());
        return respJson;
    }


    private void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }


}
