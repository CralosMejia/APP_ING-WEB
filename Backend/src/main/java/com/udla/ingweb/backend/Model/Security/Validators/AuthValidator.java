package com.udla.ingweb.backend.Model.Security.Validators;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Model.Security.Exceptions.errorMessage;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class AuthValidator {

    @Autowired
    private UserRepository userRepo;

    public void validate(Map<String,String> paramMap) throws errorMessage {


        if(Objects.isNull(paramMap) ){
            message("Params invalid");
        }
        if(!validatecredencials(paramMap.get("email"), paramMap.get("name"))){
            message("Credentials are invalid");
        }
    }

    private boolean validatecredencials(String email, String name){
        List<User> users = userRepo.findAll();
        Optional<User> usersave = users.stream().filter(user -> email.equals(user.getEmail())).findFirst();
        if(usersave.isEmpty()){
            User user = new User();
            user.setROL("USER");
            user.setName(name);
            user.setEmail(email);
            userRepo.save(user);
        }
        return true;
    };

    private void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }
}
