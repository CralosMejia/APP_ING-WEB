package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthControllerImp implements AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JWTUtil jwt;

    private List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public Map<String, Object> loginUser(String email, String password) {
        List<User> users = getUsers();
        Map<String, Object> respJson = new HashMap<String,Object>();
        Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);


        Optional<User> usersave = users.stream().filter(user -> email.equals(user.getEmail())).findFirst();
        boolean checkCredentials = argon.verify(usersave.get().getPassword(), password);

        if(checkCredentials){
            String tokenJWT =jwt.create(usersave.get().getId(),usersave.get().getEmail());;
            respJson.put("Status","OK");
            respJson.put("User",usersave.get());
            respJson.put("token",tokenJWT);
            return respJson;
        }
        respJson.put("Status","FAIL");
        return respJson;
    }
}
