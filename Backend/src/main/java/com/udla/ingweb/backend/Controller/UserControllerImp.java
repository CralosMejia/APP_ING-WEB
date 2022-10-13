package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Utils.JWTUtil;
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
    private JWTUtil jwt;

    @Override
    public Map<String, Object> createUser(User user) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        try {
            //Encrypt password
            Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String pasworddHash = argon.hash(2, 1024, 2, user.getPassword());
            user.setPassword(pasworddHash);
            //Create response
            User userSave = userRepo.save(user);
            String tokenJWT =jwt.create(userSave.getId(),userSave.getEmail());
            respJson.put("Status","OK");
            respJson.put("User",userSave);
            respJson.put("token",tokenJWT);
            return respJson;
        }catch (Exception e){
            respJson.put("Status","FAIL");
            return respJson;
        }
    }

    @Override
    public Map<String, Object> getUsers(String token) {
        Map<String, Object> respJson = new HashMap<String,Object>();


        try {
            String userID = jwt.getKey(token);
            Optional<User> findUser = userRepo.findById(userID);
            if(findUser.get() != null) {
                List<User> users = new ArrayList<User>(userRepo.findAll());
                users.forEach(user -> user.setPassword(""));
                respJson.put("Status","OK");
                respJson.put("Users",users);
                return respJson;
            }
            respJson.put("Status","FAIL");
            return respJson;
        }catch (Exception e){
            return respJson;
        }
    }

    //hay que mejorar
    @Override
    public Map<String, Object>  updateUser(String id, User user,String token){
        Map<String, Object> respJson = new HashMap<String,Object>();
        try {

            String userID = jwt.getKey(token);

            if(userID.equals(id)){
                Optional<User> usersave = userRepo.findById(id);
                user.setId(usersave.get().getId());
                user.setPassword(usersave.get().getPassword());
                userRepo.save(user);

                respJson.put("Status","OK");
                respJson.put("User",user);
                return respJson;
            }

            respJson.put("Status","FAIL");
            return respJson;
        }catch (Exception e){
            System.out.println("Update User: "+e);
            respJson.put("Status","FAIL");
            return respJson;
        }
    }

    @Override
    public Map<String, Object> deleteUser(String id, String token) {
        Map<String, Object> respJson = new HashMap<String,Object>();
        try {

            String userID = jwt.getKey(token);

            if(!userID.equals(id)){
                userRepo.deleteById(id);
                respJson.put("Status","OK");
                respJson.put("MSG","User has been deleted");
                return respJson;
            }
            respJson.put("Status","FAIL");
            respJson.put("MSG","You can't delete yourself");
            return respJson;
        }catch (Exception e){
            System.out.println("Delete User: "+e);
            respJson.put("Status","FAIL");
            return respJson;
        }
    }


}
