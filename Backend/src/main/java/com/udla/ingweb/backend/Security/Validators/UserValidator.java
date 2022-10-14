package com.udla.ingweb.backend.Security.Validators;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepo;
    private static final String CLIENT_CREDENTIALS ="client_credential";

    public void validate(User user,String grantType) throws errorMessage {

        if(!grantType.equals(CLIENT_CREDENTIALS)){
            message("Field grantType is invalid");
        }
        if(Objects.isNull(user)){
            message("User invalid");
        }

        if(user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()){
            message("All fields must be fill");
        }

        if(validationEmail(user.getEmail())){
            message("Email already exists");
        }
    }

    private boolean validationEmail(String email){
        List<User> users = new ArrayList<User>(userRepo.findAll());
        boolean validation = users.stream().anyMatch(user -> email.equals(user.getEmail()));

        return validation;
    }
    private void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }

}
