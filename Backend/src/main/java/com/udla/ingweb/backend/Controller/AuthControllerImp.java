package com.udla.ingweb.backend.Controller;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Model.Security.config.JwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class AuthControllerImp implements AuthController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtIO jwtio;



    @Override
    public Map<String, Object> generateTokenAndMenu(String email){
        Map<String, Object> respJson = new HashMap<String,Object>();

        List<User> users = userRepo.findAll();
        Optional<User> user = users.stream().filter(userP -> email.equals(userP.getEmail())).findFirst();

        String jwt = jwtio.generateToken(user.get().getId());

        respJson.put("token",jwt);
        respJson.put("User",user.get());
        respJson.put("Menu",getMenu(user.get().getROL()));
        return  respJson;

    }


    private List<String> getMenu(String rol){
        List<String> menu = new ArrayList();
        if(rol.equals("ADMIN")){
            menu.add("Update Users");
        }else if(rol.equals("USER")){
            menu.add("GET Users");
        }
        return menu;
    }
}
