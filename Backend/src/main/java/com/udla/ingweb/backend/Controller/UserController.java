package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;

import java.util.Map;

public interface UserController {


    public Map<String, Object> createUser( User user);
    public Map<String, Object> getUsers(String token);
    public Map<String, Object>  updateUser(String id, User user);
    public Map<String, Object> deleteUser(String id);
    public Map<String, Object> getUserRol(String id);
    public Map<String, Object> findUser(String id);
    public Map<String, Object>  addProdShoppingCar(String id, String prodId,int amount) throws errorMessage;
    public Map<String, Object>  deleteProdShoppingCar(String id, String prodId,int amount);
    public Map<String, Object>  buy(String userID);
    public Map<String, Object> getShoppingCar(String id);


}
