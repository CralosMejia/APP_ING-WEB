package com.udla.ingweb.backend.Security.Validators;

import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import com.udla.ingweb.backend.Security.config.InterceptorJwtIO;
import com.udla.ingweb.backend.Security.config.JwtIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtIO jwtio;


    public void validate(User user) throws errorMessage {

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

    public void validatePUT(Object user) throws errorMessage {

        if(Objects.isNull(user)){
            message("User invalid");
        }
    }



    public void validateID(String userID) throws errorMessage {
        List<User> users = new ArrayList<User>(userRepo.findAll());
        if(!users.stream().anyMatch(user -> userID.equals(user.getId()))){
            message("User doesn't exists");
        }
    }

    public void validateDeleteUser(String id) throws errorMessage {
        String tokenID = jwtio.getPayload(InterceptorJwtIO.token);
        if(id.equals(tokenID)){
            message("Can't delete yourself.");
        }
    }

    public void rolValidate(String UserID) throws errorMessage {
        Optional<User> usersave = userRepo.findById(UserID);
        if(!usersave.get().getROL().equals("ADMIN")){
            message("You don't have permisions to do this action");
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
