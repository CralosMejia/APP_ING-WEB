package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Security.config.JwtIO;
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


}
