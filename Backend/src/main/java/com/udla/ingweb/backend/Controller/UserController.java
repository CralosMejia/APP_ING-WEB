package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;

import java.util.List;
import java.util.Map;

public interface UserController {


    public User createUser( User user);
    public Map<String, Object> getUsers(String token);
    public Map<String, Object>  updateUser(String id, User user, String token);
    public Map<String, Object> deleteUser(String id,String token);

}
