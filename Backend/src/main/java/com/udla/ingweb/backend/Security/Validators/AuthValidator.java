package com.udla.ingweb.backend.Security.Validators;

import com.udla.ingweb.backend.Controller.AuthController;
import com.udla.ingweb.backend.Entity.User;
import com.udla.ingweb.backend.Model.UserRepository;
import com.udla.ingweb.backend.Security.Exceptions.errorMessage;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class AuthValidator {

    private static final String CLIENT_CREDENTIALS ="client_credential";

    @Autowired
    private UserRepository userRepo;

    public void validate(MultiValueMap<String,String> paramMap, String grantType) throws errorMessage {

        if(!grantType.equals(CLIENT_CREDENTIALS)){
            message("Field grantType is invalid");
        }

        if(Objects.isNull(paramMap) ){
            message("Params invalid");
        }
        if(!validatecredencials(paramMap.getFirst("password"),paramMap.getFirst("email"))){
            message("Credentials are invalid");
        }
    }

    private boolean validatecredencials(String password, String email){
        List<User> users = userRepo.findAll();
        Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        Optional<User> usersave = users.stream().filter(user -> email.equals(user.getEmail())).findFirst();
        if(usersave.isEmpty()){
            return false;
        }
        boolean checkCredentials = argon.verify(usersave.get().getPassword(), password);
        return checkCredentials;
    };

    private void message(String message) throws errorMessage {
        System.out.println(message);
        throw new errorMessage(message);
    }
}
