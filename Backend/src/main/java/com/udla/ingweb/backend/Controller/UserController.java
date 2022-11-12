package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;

import java.util.List;
import java.util.Map;

public interface UserController {


    public Map<String, Object> createUser( User user);
    public Map<String, Object> getUsers(String token);
    public Map<String, Object>  updateUser(String id, User user);
    public Map<String, Object> deleteUser(String id);
    public Map<String, Object> getUserRol(String id);
    public Map<String, Object> findUser(String id);


}
